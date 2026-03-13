package io.github.jderstd.spring.response.json

import io.github.jderstd.spring.response.json.functions.CreateFailureJsonResponseFunctions
import io.github.jderstd.spring.response.json.functions.CreateSuccessJsonResponseFunctions
import org.springframework.http.ResponseEntity
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CreateJsonResponseTest {
    @Test
    fun `success creates a json response with payload`() {
        val responseBuilder: CreateSuccessJsonResponseFunctions<String> = CreateJsonResponse.success()
        responseBuilder.status = 202
        responseBuilder.json.data = "done"

        val response: ResponseEntity<JsonResponse<String>> = responseBuilder.create()
        val body: JsonResponse<String> = assertNotNull(response.body)

        assertEquals(202, response.statusCode.value())
        assertEquals(listOf("application/json"), response.headers["Content-Type"])
        assertTrue(body.success)
        assertEquals("done", body.data)
        assertTrue(body.errors.isEmpty())
    }

    @Test
    fun `failure creates an unsuccessful json response with errors`() {
        val responseBuilder: CreateFailureJsonResponseFunctions<String> = CreateJsonResponse.failure()
        responseBuilder.status = 400

        val error: JsonResponseError =
            JsonResponseError().apply {
                code = "bad_request"
                path = listOf("email")
                message = "Email is invalid."
            }

        responseBuilder.addError(error)

        val response: ResponseEntity<JsonResponse<String>> = responseBuilder.create()
        val body: JsonResponse<String> = assertNotNull(response.body)
        val firstError: JsonResponseError = body.error()

        assertEquals(400, response.statusCode.value())
        assertEquals(listOf("application/json"), response.headers["Content-Type"])
        assertFalse(body.success)
        assertNull(body.data)
        assertEquals("bad_request", firstError.code)
        assertEquals(listOf("email"), firstError.path)
        assertEquals("Email is invalid.", firstError.message)
    }
}
