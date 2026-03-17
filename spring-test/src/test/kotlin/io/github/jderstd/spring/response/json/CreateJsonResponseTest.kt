package io.github.jderstd.spring.response.json

import io.github.jderstd.spring.response.json.create.CreateFailureJsonResponse
import io.github.jderstd.spring.response.json.create.CreateSuccessJsonResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CreateJsonResponseTest {
    @Test
    fun `dataless creates a successful json response with defaults`() {
        val response: ResponseEntity<JsonResponse<Unit>> = CreateJsonResponse.dataless().create()

        val body: JsonResponse<Unit> = assertNotNull(response.body)

        assertEquals(200, response.statusCode.value())
        assertEquals(listOf("application/json"), response.headers["Content-Type"])
        assertTrue(body.success)
        assertNull(body.data)
        assertTrue(body.errors.isEmpty())
    }

    @Test
    fun `success creates a successful json response with defaults`() {
        val response: ResponseEntity<JsonResponse<String>> = CreateJsonResponse.success<String>().create()

        val body: JsonResponse<String> = assertNotNull(response.body)

        assertEquals(200, response.statusCode.value())
        assertEquals(listOf("application/json"), response.headers["Content-Type"])
        assertTrue(body.success)
        assertNull(body.data)
        assertTrue(body.errors.isEmpty())
    }

    @Test
    fun `success creates a json response with payload`() {
        val response: ResponseEntity<JsonResponse<String>> =
            CreateJsonResponse
                .success<String>()
                .status(202)
                .data("done")
                .create()

        val body: JsonResponse<String> = assertNotNull(response.body)

        assertEquals(202, response.statusCode.value())
        assertEquals(listOf("application/json"), response.headers["Content-Type"])
        assertTrue(body.success)
        assertEquals("done", body.data)
        assertTrue(body.errors.isEmpty())
    }

    @Test
    fun `success builder data property updates the response body`() {
        val response: ResponseEntity<JsonResponse<String>> =
            CreateJsonResponse
                .success<String>()
                .status(202)
                .data("done")
                .create()

        val body: JsonResponse<String> = assertNotNull(response.body)

        assertEquals(202, response.statusCode.value())
        assertEquals(listOf("application/json"), response.headers["Content-Type"])
        assertTrue(body.success)
        assertEquals("done", body.data)
        assertTrue(body.errors.isEmpty())
    }

    @Test
    fun `success builder reuse does not mutate earlier responses`() {
        val builder: CreateSuccessJsonResponse<String> =
            CreateJsonResponse
                .success<String>()
                .data("first")

        val firstResponse: ResponseEntity<JsonResponse<String>> = builder.create()

        builder.data("second")

        val secondResponse: ResponseEntity<JsonResponse<String>> = builder.create()
        val firstBody: JsonResponse<String> = assertNotNull(firstResponse.body)
        val secondBody: JsonResponse<String> = assertNotNull(secondResponse.body)

        assertEquals("first", firstBody.data)
        assertEquals("second", secondBody.data)
    }

    @Test
    fun `failure creates an unsuccessful json response with errors`() {
        val error: JsonResponseError =
            JsonResponseError()
                .code("bad_request")
                .path(listOf("email"))
                .message("Email is invalid.")

        val response: ResponseEntity<JsonResponse<Unit>> =
            CreateJsonResponse
                .failure()
                .addError(error)
                .create()

        val body: JsonResponse<Unit> = assertNotNull(response.body)

        val firstError: JsonResponseError? = body.error()

        assertEquals(400, response.statusCode.value())
        assertEquals(listOf("application/json"), response.headers["Content-Type"])
        assertFalse(body.success)
        assertNull(body.data)
        assertEquals("bad_request", firstError?.code)
        assertEquals(listOf("email"), firstError?.path)
        assertEquals("Email is invalid.", firstError?.message)
    }

    @Test
    fun `failure without errors stays unsuccessful and error access returns null`() {
        val response: ResponseEntity<JsonResponse<Unit>> = CreateJsonResponse.failure().create()

        val body: JsonResponse<Unit> = assertNotNull(response.body)

        assertFalse(body.success)
        assertTrue(body.errors.isEmpty())
        assertNull(body.error())
    }

    @Test
    fun `failure addErrors keeps error order`() {
        val emailError: JsonResponseError =
            JsonResponseError()
                .code("bad_request")
                .path(listOf("email"))
                .message("Email is invalid.")

        val nameError: JsonResponseError =
            JsonResponseError()
                .code("missing_name")
                .path(listOf("name"))
                .message("Name is required.")

        val response: ResponseEntity<JsonResponse<Unit>> =
            CreateJsonResponse
                .failure()
                .addErrors(listOf(emailError, nameError))
                .create()

        val body: JsonResponse<Unit> = assertNotNull(response.body)

        assertEquals(listOf(emailError, nameError), body.errors)
        assertEquals(emailError, body.error())
    }

    @Test
    fun `failure builder errors property updates the response body`() {
        val error: JsonResponseError =
            JsonResponseError()
                .code("bad_request")
                .path(listOf("email"))
                .message("Email is invalid.")

        val response: ResponseEntity<JsonResponse<Unit>> =
            CreateJsonResponse
                .failure()
                .status(422)
                .errors(listOf(error))
                .create()

        val body: JsonResponse<Unit> = assertNotNull(response.body)

        assertEquals(422, response.statusCode.value())
        assertEquals(listOf("application/json"), response.headers["Content-Type"])
        assertFalse(body.success)
        assertNull(body.data)
        assertEquals(listOf(error), body.errors)
        assertEquals(error, body.error())
    }

    @Test
    fun `failure builder keeps fluent chaining after addError`() {
        val error: JsonResponseError =
            JsonResponseError()
                .code("bad_request")
                .path(listOf("email"))
                .message("Email is invalid.")

        val response: ResponseEntity<JsonResponse<Unit>> =
            CreateJsonResponse
                .failure()
                .status(400)
                .addError(error)
                .create()

        val body: JsonResponse<Unit> = assertNotNull(response.body)

        assertEquals(400, response.statusCode.value())
        assertFalse(body.success)
        assertEquals(error, body.error())
    }

    @Test
    fun `failure builder reuse does not mutate earlier responses`() {
        val firstError: JsonResponseError =
            JsonResponseError()
                .code("bad_request")
                .message("Email is invalid.")

        val secondError: JsonResponseError =
            JsonResponseError()
                .code("missing_name")
                .message("Name is required.")

        val builder: CreateFailureJsonResponse<Unit> =
            CreateJsonResponse
                .failure()
                .addError(firstError)

        val firstResponse: ResponseEntity<JsonResponse<Unit>> = builder.create()

        builder.addError(secondError)

        val secondResponse: ResponseEntity<JsonResponse<Unit>> = builder.create()
        val firstBody: JsonResponse<Unit> = assertNotNull(firstResponse.body)
        val secondBody: JsonResponse<Unit> = assertNotNull(secondResponse.body)

        assertEquals(listOf(firstError), firstBody.errors)
        assertEquals(listOf(firstError, secondError), secondBody.errors)
    }

    @Test
    fun `json create enforces a single application json content type`() {
        val response: ResponseEntity<JsonResponse<String>> =
            CreateJsonResponse
                .success<String>()
                .addHeader("Content-Type", "text/plain")
                .create()

        assertEquals(listOf("application/json"), response.headers["Content-Type"])
    }

    @Test
    fun `json create preserves custom headers`() {
        val response: ResponseEntity<JsonResponse<String>> =
            CreateJsonResponse
                .success<String>()
                .addHeader("X-Request-Id", "req-123")
                .addHeader("Content-Type", "text/plain")
                .create()

        assertEquals(listOf("req-123"), response.headers["X-Request-Id"])
        assertEquals(listOf("application/json"), response.headers["Content-Type"])
    }

    @Test
    fun `json create copies read only headers before enforcing content type`() {
        val sourceHeaders: HttpHeaders = HttpHeaders()

        sourceHeaders.add("X-Request-Id", "req-123")

        val builder =
            CreateJsonResponse
                .success<String>()
                .headers(HttpHeaders.readOnlyHttpHeaders(sourceHeaders))

        sourceHeaders.add("X-Late", "late")

        val response: ResponseEntity<JsonResponse<String>> = builder.create()

        assertEquals(listOf("req-123"), response.headers["X-Request-Id"])
        assertNull(response.headers["X-Late"])
        assertEquals(listOf("application/json"), response.headers["Content-Type"])
        assertNull(sourceHeaders["Content-Type"])
    }
}
