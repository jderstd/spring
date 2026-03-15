@file:Suppress("RedundantExplicitType")

package io.github.jderstd.spring.response.json

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class JsonResponseTest {
    @Test
    fun `json response addError appends a single error`() {
        val response: JsonResponse<String> = JsonResponse()

        val error: JsonResponseError =
            JsonResponseError().code("single")

        response.addError(error)

        assertEquals(listOf(error), response.errors)
        assertEquals(error, response.error())
    }

    @Test
    fun `json response addErrors appends errors in order`() {
        val response: JsonResponse<String> = JsonResponse()

        val firstError: JsonResponseError =
            JsonResponseError().code("first")

        val secondError: JsonResponseError =
            JsonResponseError().code("second")

        response.addErrors(listOf(firstError, secondError))

        assertEquals(listOf(firstError, secondError), response.errors)
        assertEquals(firstError, response.error())
    }

    @Test
    fun `json response fluent setters can be chained`() {
        val firstError: JsonResponseError =
            JsonResponseError()
                .code("bad_request")
                .path(listOf("email"))
                .message("Email is invalid.")

        val secondError: JsonResponseError = JsonResponseError().code("missing_name")

        val response: JsonResponse<String> =
            JsonResponse<String>()
                .success(false)
                .data("payload")
                .errors(listOf(firstError))
                .addError(secondError)

        assertFalse(response.success)
        assertEquals("payload", response.data)
        assertEquals(listOf("email"), firstError.path)
        assertEquals("Email is invalid.", firstError.message)
        assertEquals(listOf(firstError, secondError), response.errors)
    }

    @Test
    fun `json response errors setter copies the provided list`() {
        val firstError: JsonResponseError = JsonResponseError().code("first")

        val providedErrors: MutableList<JsonResponseError> = mutableListOf(firstError)

        val response: JsonResponse<String> = JsonResponse<String>().errors(providedErrors)

        providedErrors.add(JsonResponseError().code("second"))

        assertEquals(listOf(firstError), response.errors)
        assertEquals(firstError, response.error())
    }

    @Test
    fun `json response error defaults stay predictable`() {
        val error: JsonResponseError = JsonResponseError()

        assertEquals(ResponseError.UNKNOWN.code(), error.code)
        assertTrue(error.path.isEmpty())
        assertNull(error.message)
    }
}
