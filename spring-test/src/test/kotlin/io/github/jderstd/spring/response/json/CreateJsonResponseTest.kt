package io.github.jderstd.spring.response.json

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
                .json(JsonResponse<String>().data("done"))
                .create()

        val body: JsonResponse<String> = assertNotNull(response.body)

        assertEquals(202, response.statusCode.value())
        assertEquals(listOf("application/json"), response.headers["Content-Type"])
        assertTrue(body.success)
        assertEquals("done", body.data)
        assertTrue(body.errors.isEmpty())
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
    fun `failure json setter keeps fluent chaining on the failure builder`() {
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
}
