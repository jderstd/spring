@file:Suppress("RedundantExplicitType")

package io.github.jderstd.spring.handler

import io.github.jderstd.spring.response.json.JsonResponse
import io.github.jderstd.spring.response.json.JsonResponseError
import io.github.jderstd.spring.response.json.ResponseError
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class NotFoundHandlerTest {
    @Test
    fun `handle creates a 404 not found response`() {
        val response: ResponseEntity<JsonResponse<Void>> = NotFoundHandler.handle()

        val body: JsonResponse<Void> = assertNotNull(response.body)
        val error: JsonResponseError = assertNotNull(body.error())

        val targetError: ResponseError = ResponseError.NOT_FOUND

        assertEquals(404, response.statusCode.value())
        assertEquals(listOf("application/json"), response.headers["Content-Type"])
        assertFalse(body.success)
        assertNull(body.data)
        assertEquals(targetError.code(), error.code)
        assertTrue(error.path.isEmpty())
        assertEquals(targetError.message(), error.message)
    }

    @Test
    fun `handle preserves headers from the provided error response`() {
        val headers: HttpHeaders = HttpHeaders()

        headers.add("X-Request-Id", "req-123")

        val response: ResponseEntity<JsonResponse<Void>> =
            NotFoundHandler.handle(createErrorResponse(headers))

        assertEquals(listOf("req-123"), response.headers["X-Request-Id"])
        assertEquals(listOf("application/json"), response.headers["Content-Type"])
    }

    private fun createErrorResponse(headers: HttpHeaders): ErrorResponse =
        object : ErrorResponse {
            override fun getStatusCode(): HttpStatus = HttpStatus.NOT_FOUND

            override fun getHeaders(): HttpHeaders = headers

            override fun getBody(): ProblemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        }
}
