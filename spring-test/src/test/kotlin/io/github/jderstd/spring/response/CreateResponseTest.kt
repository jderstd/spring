package io.github.jderstd.spring.response

import org.springframework.http.ResponseEntity
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CreateResponseTest {
    @Test
    fun `create returns default response when not configured`() {
        val response: ResponseEntity<String> = CreateResponse<String>().create()

        assertEquals(200, response.statusCode.value())
        assertNull(response.body)
        assertTrue(response.headers.isEmpty)
    }

    @Test
    fun `create returns the configured response`() {
        val response: ResponseEntity<String> =
            CreateResponse<String>()
                .status(201)
                .body("created")
                .addHeader("X-Request-Id", "req-123")
                .addHeader("X-Trace", listOf("trace-1", "trace-2"))
                .create()

        assertEquals(201, response.statusCode.value())
        assertEquals("created", response.body)
        assertEquals(listOf("req-123"), response.headers["X-Request-Id"])
        assertEquals(listOf("trace-1", "trace-2"), response.headers["X-Trace"])
    }

    @Test
    fun `addHeaders adds string and iterable header values`() {
        val responseBuilder: CreateResponse<String> = CreateResponse()

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
    fun `addHeader overloads append values in order`() {
        val responseBuilder: CreateResponse<String> = CreateResponse()

        responseBuilder.addHeader("X-Trace", "trace-1")
        responseBuilder.addHeader("X-Trace", listOf("trace-2", "trace-3"))

        assertEquals(listOf("trace-1", "trace-2", "trace-3"), responseBuilder.headers["X-Trace"])
    }

    @Test
    fun `addHeaders rejects unsupported header values`() {
        val responseBuilder: CreateResponse<String> = CreateResponse()

        val exception: IllegalArgumentException =
            assertFailsWith<IllegalArgumentException> {
                responseBuilder.addHeaders(mapOf("X-Invalid" to 1))
            }

        assertNotNull(exception.message)
        assertTrue(exception.message!!.contains("X-Invalid"))
    }

    @Test
    fun `addHeaders rejects iterables containing non string values`() {
        val responseBuilder: CreateResponse<String> = CreateResponse()

        val exception: IllegalArgumentException =
            assertFailsWith<IllegalArgumentException> {
                responseBuilder.addHeaders(
                    mapOf<String, Any>("X-Mixed" to listOf<Any>("value-1", 2)),
                )
            }

        assertNotNull(exception.message)
        assertTrue(exception.message!!.contains("X-Mixed"))
        assertTrue(responseBuilder.headers.isEmpty)
    }

    @Test
    fun `addHeaders rejects iterables containing null values`() {
        val responseBuilder: CreateResponse<String> = CreateResponse()

        val exception: IllegalArgumentException =
            assertFailsWith<IllegalArgumentException> {
                responseBuilder.addHeaders(
                    mapOf<String, Any>("X-Nullable" to listOf<Any?>("value-1", null)),
                )
            }

        assertNotNull(exception.message)
        assertTrue(exception.message!!.contains("X-Nullable"))
        assertTrue(responseBuilder.headers.isEmpty)
    }
}
