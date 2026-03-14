package io.github.jderstd.spring.handler;

import java.util.List;

import io.github.jderstd.spring.response.json.JsonResponse;
import io.github.jderstd.spring.response.json.JsonResponseError;
import io.github.jderstd.spring.response.json.ResponseError;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotFoundHandlerJavaTest {
    @Test
    void handleCreatesA404NotFoundResponse() {
        ResponseEntity<JsonResponse<Void>> response = NotFoundHandler.handle();

        JsonResponse<Void> body = response.getBody();

        assertNotNull(body);
        JsonResponseError error = body.error();

        ResponseError targetError = ResponseError.NOT_FOUND;

        assertNotNull(error);
        assertEquals(404, response.getStatusCode().value());
        assertEquals(List.of("application/json"), response.getHeaders().get("Content-Type"));
        assertFalse(body.getSuccess());
        assertNull(body.getData());
        assertEquals(targetError.getCode(), error.getCode());
        assertTrue(error.getPath().isEmpty());
        assertEquals(targetError.getMessage(), error.getMessage());
    }

    @Test
    void handlePreservesHeadersFromTheProvidedErrorResponse() {
        HttpHeaders headers = new HttpHeaders();

        headers.add("X-Request-Id", "req-123");

        ResponseEntity<JsonResponse<Void>> response =
            NotFoundHandler.handle(createErrorResponse(headers));

        assertEquals(List.of("req-123"), response.getHeaders().get("X-Request-Id"));
        assertEquals(List.of("application/json"), response.getHeaders().get("Content-Type"));
    }

    private ErrorResponse createErrorResponse(HttpHeaders headers) {
        return new ErrorResponse() {
            @Override
            @NotNull
            public HttpStatus getStatusCode() {
                return HttpStatus.NOT_FOUND;
            }

            @Override
            @NotNull
            public HttpHeaders getHeaders() {
                return headers;
            }

            @Override
            @NotNull
            public ProblemDetail getBody() {
                return ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
            }
        };
    }
}
