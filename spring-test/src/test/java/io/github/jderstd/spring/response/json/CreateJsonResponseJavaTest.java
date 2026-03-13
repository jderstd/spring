package io.github.jderstd.spring.response.json;

import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateJsonResponseJavaTest {
    @Test
    void datalessCreatesASuccessfulJsonResponseWithDefaults() {
        ResponseEntity<JsonResponse<Void>> response = CreateJsonResponse.dataless().create();

        JsonResponse<Void> body = response.getBody();

        assertNotNull(body);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(List.of("application/json"), response.getHeaders().get("Content-Type"));
        assertTrue(body.getSuccess());
        assertNull(body.getData());
        assertTrue(body.getErrors().isEmpty());
    }

    @Test
    void successCreatesASuccessfulJsonResponseWithDefaults() {
        ResponseEntity<JsonResponse<String>> response = CreateJsonResponse.<String>success().create();

        JsonResponse<String> body = response.getBody();

        assertNotNull(body);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(List.of("application/json"), response.getHeaders().get("Content-Type"));
        assertTrue(body.getSuccess());
        assertNull(body.getData());
        assertTrue(body.getErrors().isEmpty());
    }

    @Test
    void successCreatesAJsonResponseWithPayload() {
        ResponseEntity<JsonResponse<String>> response = CreateJsonResponse.<String>success()
                .status(202)
                .json(new JsonResponse<String>().data("done"))
                .create();

        JsonResponse<String> body = response.getBody();

        assertNotNull(body);
        assertEquals(202, response.getStatusCode().value());
        assertEquals(List.of("application/json"), response.getHeaders().get("Content-Type"));
        assertTrue(body.getSuccess());
        assertEquals("done", body.getData());
        assertTrue(body.getErrors().isEmpty());
    }

    @Test
    void failureCreatesAnUnsuccessfulJsonResponseWithErrors() {
        JsonResponseError error = createError("bad_request", List.of("email"), "Email is invalid.");

        ResponseEntity<JsonResponse<Void>> response = CreateJsonResponse.failure()
                .addError(error)
                .create();

        JsonResponse<Void> body = response.getBody();

        assertNotNull(body);
        JsonResponseError firstError = body.error();

        assertEquals(400, response.getStatusCode().value());
        assertEquals(List.of("application/json"), response.getHeaders().get("Content-Type"));
        assertFalse(body.getSuccess());
        assertNull(body.getData());
        assertNotNull(firstError);
        assertEquals("bad_request", firstError.getCode());
        assertEquals(List.of("email"), firstError.getPath());
        assertEquals("Email is invalid.", firstError.getMessage());
    }

    @Test
    void failureWithoutErrorsStaysUnsuccessfulAndErrorAccessReturnsNull() {
        ResponseEntity<JsonResponse<Void>> response = CreateJsonResponse.failure().create();

        JsonResponse<Void> body = response.getBody();

        assertNotNull(body);
        assertFalse(body.getSuccess());
        assertTrue(body.getErrors().isEmpty());
        assertNull(body.error());
    }

    @Test
    void failureAddErrorsKeepsErrorOrder() {
        JsonResponseError emailError = createError("bad_request", List.of("email"), "Email is invalid.");

        JsonResponseError nameError = createError("missing_name", List.of("name"), "Name is required.");

        ResponseEntity<JsonResponse<Void>> response = CreateJsonResponse
                .failure()
                .addErrors(List.of(emailError, nameError))
                .create();

        JsonResponse<Void> body = response.getBody();

        assertNotNull(body);
        assertEquals(List.of(emailError, nameError), body.getErrors());
        assertEquals(emailError, body.error());
    }

    @Test
    void failureJsonSetterKeepsFluentChainingOnTheFailureBuilder() {
        JsonResponseError error = new JsonResponseError()
            .code("bad_request")
            .path(List.of("email"))
            .message("Email is invalid.");

        ResponseEntity<JsonResponse<Void>> response = CreateJsonResponse.failure()
            .addError(error)
            .create();

        JsonResponse<Void> body = response.getBody();

        assertNotNull(body);
        assertEquals(400, response.getStatusCode().value());
        assertFalse(body.getSuccess());
        assertEquals(error, body.error());
    }

    @Test
    void jsonCreateEnforcesASingleApplicationJsonContentType() {
        ResponseEntity<JsonResponse<String>> response = CreateJsonResponse
                .<String>success()
                .addHeader("Content-Type", "text/plain")
                .create();

        assertEquals(List.of("application/json"), response.getHeaders().get("Content-Type"));
    }

    @Test
    void jsonCreatePreservesCustomHeaders() {
        ResponseEntity<JsonResponse<String>> response = CreateJsonResponse
                .<String>success()
                .addHeader("X-Request-Id", "req-123")
                .addHeader("Content-Type", "text/plain")
                .create();

        assertEquals(List.of("req-123"), response.getHeaders().get("X-Request-Id"));
        assertEquals(List.of("application/json"), response.getHeaders().get("Content-Type"));
    }

    private JsonResponseError createError(String code, List<String> path, String message) {
        JsonResponseError error = new JsonResponseError();
        error.setCode(code);
        error.setPath(path);
        error.setMessage(message);
        return error;
    }

    private <T> T configure(T value, Consumer<T> configure) {
        configure.accept(value);
        return value;
    }

    private <T> T tap(T value, Consumer<T> consumer) {
        consumer.accept(value);
        return value;
    }
}
