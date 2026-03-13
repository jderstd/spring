package io.github.jderstd.spring.response

import io.github.jderstd.spring.response.base.CreateBaseResponse
import org.springframework.http.ResponseEntity
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CreateResponseTest {
    @Test
    fun `create returns the configured response`() {
        val responseBuilder: CreateResponse<String> = CreateResponse()
        responseBuilder.status = 201
        responseBuilder.body = "created"
        responseBuilder.addHeader("X-Request-Id", "req-123")
        responseBuilder.addHeader("X-Trace", listOf("trace-1", "trace-2"))

        val response: ResponseEntity<String> = responseBuilder.create()

        assertEquals(201, response.statusCode.value())
        assertEquals("created", response.body)
        assertEquals(listOf("req-123"), response.headers["X-Request-Id"])
        assertEquals(listOf("trace-1", "trace-2"), response.headers["X-Trace"])
    }

    @Test
    fun `addHeaders adds string and iterable header values`() {
        val responseBuilder: CreateBaseResponse = CreateBaseResponse()

        responseBuilder.addHeaders(
            mapOf(
                "X-One" to "value-1",
                "X-Many" to listOf("value-2", "value-3"),
            ),
        )

        assertEquals(listOf("value-1"), responseBuilder.headers["X-One"])
        assertEquals(listOf("value-2", "value-3"), responseBuilder.headers["X-Many"])
    }

    @Test
    fun `addHeaders rejects unsupported header values`() {
        val responseBuilder: CreateBaseResponse = CreateBaseResponse()

        val exception: IllegalArgumentException =
            assertFailsWith<IllegalArgumentException> {
                responseBuilder.addHeaders(mapOf("X-Invalid" to 1))
            }

        assertNotNull(exception.message)
        assertTrue(exception.message!!.contains("X-Invalid"))
    }
}
