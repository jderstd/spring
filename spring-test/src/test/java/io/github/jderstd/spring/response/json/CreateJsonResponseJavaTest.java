package io.github.jderstd.spring.response.json;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
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
                .setStatus(202)
                .setData("done")
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
    void successSetDataWritesTheResponseBody() {
        ResponseEntity<JsonResponse<String>> response = CreateJsonResponse.<String>success()
                .setStatus(202)
                .setData("done")
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

        JsonResponseError firstError = body.getError();

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
        assertNull(body.getError());
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
        assertEquals(emailError, body.getError());
    }

    @Test
    void failureSetErrorsWritesTheResponseBody() {
        JsonResponseError error = createError("bad_request", List.of("email"), "Email is invalid.");

        ResponseEntity<JsonResponse<Void>> response = CreateJsonResponse.failure()
                .setStatus(422)
                .setErrors(List.of(error))
                .create();

        JsonResponse<Void> body = response.getBody();

        assertNotNull(body);
        assertEquals(422, response.getStatusCode().value());
        assertEquals(List.of("application/json"), response.getHeaders().get("Content-Type"));
        assertFalse(body.getSuccess());
        assertNull(body.getData());
        assertEquals(List.of(error), body.getErrors());
        assertEquals(error, body.getError());
    }

    @Test
    void failureBuilderKeepsFluentChainingAfterAddError() {
        JsonResponseError error = new JsonResponseError()
            .setCode("bad_request")
            .setPath(List.of("email"))
            .setMessage("Email is invalid.");

        ResponseEntity<JsonResponse<Void>> response = CreateJsonResponse.failure()
            .addError(error)
            .create();

        JsonResponse<Void> body = response.getBody();

        assertNotNull(body);
        assertEquals(400, response.getStatusCode().value());
        assertFalse(body.getSuccess());
        assertEquals(error, body.getError());
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

    @Test
    void jsonCreateCopiesReadOnlyHeadersBeforeEnforcingContentType() {
        HttpHeaders sourceHeaders = new HttpHeaders();

        sourceHeaders.add("X-Request-Id", "req-123");

        var builder = CreateJsonResponse
                .<String>success()
                .setHeaders(HttpHeaders.readOnlyHttpHeaders(sourceHeaders));

        sourceHeaders.add("X-Late", "late");

        ResponseEntity<JsonResponse<String>> response = builder.create();

        assertEquals(List.of("req-123"), response.getHeaders().get("X-Request-Id"));
        assertNull(response.getHeaders().get("X-Late"));
        assertEquals(List.of("application/json"), response.getHeaders().get("Content-Type"));
        assertNull(sourceHeaders.get("Content-Type"));
    }

    private JsonResponseError createError(String code, List<String> path, String message) {
        JsonResponseError error = new JsonResponseError();
        error.setCode(code);
        error.setPath(path);
        error.setMessage(message);
        return error;
    }
}
