package io.github.jderstd.spring.response.json;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonResponseJavaTest {
    @Test
    void jsonResponseAddErrorAppendsASingleError() {
        JsonResponse<String> response = new JsonResponse<>();

        JsonResponseError error = createError("single");

        response.addError(error);

        assertEquals(List.of(error), response.getErrors());
        assertEquals(error, response.getError());
    }

    @Test
    void jsonResponseAddErrorsAppendsErrorsInOrder() {
        JsonResponse<String> response = new JsonResponse<>();

        JsonResponseError firstError = createError("first");

        JsonResponseError secondError = createError("second");

        response.addErrors(List.of(firstError, secondError));

        assertEquals(List.of(firstError, secondError), response.getErrors());
        assertEquals(firstError, response.getError());
    }

    @Test
    void jsonResponseFluentSettersCanBeChained() {
        JsonResponseError firstError = new JsonResponseError()
            .setCode("bad_request")
            .setPath(List.of("email"))
            .setMessage("Email is invalid.");

        JsonResponseError secondError = new JsonResponseError().setCode("missing_name");

        JsonResponse<String> response = new JsonResponse<String>()
            .setSuccess(false)
            .setData("payload")
            .setErrors(new ArrayList<>(List.of(firstError)))
            .addError(secondError);

        assertFalse(response.getSuccess());
        assertEquals("payload", response.getData());
        assertEquals(List.of("email"), firstError.getPath());
        assertEquals("Email is invalid.", firstError.getMessage());
        assertEquals(List.of(firstError, secondError), response.getErrors());
    }

    @Test
    void jsonResponseErrorDefaultsStayPredictable() {
        JsonResponseError error = new JsonResponseError();

        assertEquals(ResponseError.UNKNOWN.getCode(), error.getCode());
        assertTrue(error.getPath().isEmpty());
        assertNull(error.getMessage());
    }

    private JsonResponseError createError(String code) {
        JsonResponseError error = new JsonResponseError();
        error.setCode(code);
        return error;
    }
}
