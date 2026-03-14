package io.github.jderstd.spring.response;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("RedundantTypeArguments")
class CreateResponseJavaTest {
    @Test
    void createReturnsDefaultResponseWhenNotConfigured() {
        ResponseEntity<String> response = new CreateResponse<String>().create();

        assertEquals(200, response.getStatusCode().value());
        assertNull(response.getBody());
        assertTrue(response.getHeaders().isEmpty());
    }

    @Test
    void createReturnsTheConfiguredResponse() {
        ResponseEntity<String> response = new CreateResponse<String>()
            .setStatus(201)
            .setBody("created")
            .addHeader("X-Request-Id", "req-123")
            .addHeader("X-Trace", List.of("trace-1", "trace-2"))
            .create();

        assertEquals(201, response.getStatusCode().value());
        assertEquals("created", response.getBody());
        assertEquals(List.of("req-123"), response.getHeaders().get("X-Request-Id"));
        assertEquals(List.of("trace-1", "trace-2"), response.getHeaders().get("X-Trace"));
    }

    @Test
    void addHeadersAddsStringAndIterableHeaderValues() {
        CreateResponse<String> responseBuilder = new CreateResponse<>();

        responseBuilder.addHeaders(
            Map.<String, Object>of(
                "X-One", "value-1",
                "X-Many", List.of("value-2", "value-3")
            )
        );

        assertEquals(List.of("value-1"), responseBuilder.getHeaders().get("X-One"));
        assertEquals(List.of("value-2", "value-3"), responseBuilder.getHeaders().get("X-Many"));
    }

    @Test
    void addHeaderOverloadsAppendValuesInOrder() {
        CreateResponse<String> responseBuilder = new CreateResponse<>();

        responseBuilder.addHeader("X-Trace", "trace-1");
        responseBuilder.addHeader("X-Trace", List.of("trace-2", "trace-3"));

        assertEquals(List.of("trace-1", "trace-2", "trace-3"), responseBuilder.getHeaders().get("X-Trace"));
    }

    @Test
    void addHeadersRejectsUnsupportedHeaderValues() {
        CreateResponse<String> responseBuilder = new CreateResponse<>();

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> responseBuilder.addHeaders(Map.<String, Object>of("X-Invalid", 1))
        );

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("X-Invalid"));
    }

    @Test
    void addHeadersRejectsIterablesContainingNonStringValues() {
        CreateResponse<String> responseBuilder = new CreateResponse<>();

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> responseBuilder.addHeaders(
                Map.<String, Object>of("X-Mixed", Arrays.<Object>asList("value-1", 2))
            )
        );

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("X-Mixed"));
        assertTrue(responseBuilder.getHeaders().isEmpty());
    }

    @Test
    void addHeadersRejectsIterablesContainingNullValues() {
        CreateResponse<String> responseBuilder = new CreateResponse<>();

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> responseBuilder.addHeaders(
                Map.<String, Object>of("X-Nullable", Arrays.asList("value-1", null))
            )
        );

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("X-Nullable"));
        assertTrue(responseBuilder.getHeaders().isEmpty());
    }

    @Test
    void addHeadersDoesNotKeepEarlierHeadersWhenALaterValueIsInvalid() {
        CreateResponse<String> responseBuilder = new CreateResponse<>();

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> responseBuilder.addHeaders(
                Map.<String, Object>ofEntries(
                    Map.entry("X-One", "value-1"),
                    Map.entry("X-Invalid", 1)
                )
            )
        );

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("X-Invalid"));
        assertTrue(responseBuilder.getHeaders().isEmpty());
    }
}
