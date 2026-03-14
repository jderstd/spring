package io.github.jderstd.spring.handlers

import io.github.jderstd.spring.response.json.CreateJsonResponse
import io.github.jderstd.spring.response.json.JsonResponse
import io.github.jderstd.spring.response.json.JsonResponseError
import io.github.jderstd.spring.response.json.ResponseError
import io.github.jderstd.spring.response.json.functions.CreateFailureJsonResponseFunctions
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse

/**
 * Not found handler.
 */
public class NotFoundHandler {
    public companion object {
        private val error: ResponseError = ResponseError.NOT_FOUND
        private val STATUS: Int = error.status()
        private val CODE: String = error.code()
        private val MESSAGE: String = error.message()

        /**
         * Create a 404 not found JSON response.
         */
        @JvmStatic
        @JvmOverloads
        public fun handle(errorResponse: ErrorResponse? = null): ResponseEntity<JsonResponse<Void>> {
            val builder: CreateFailureJsonResponseFunctions<Void> =
                CreateJsonResponse
                    .failure(null)
                    .status(STATUS)
                    .addError(
                        JsonResponseError()
                            .code(CODE)
                            .message(MESSAGE),
                    )

            val headers: HttpHeaders? = errorResponse?.headers

            if (headers != null) {
                for (headerName: String in headers.headerNames()) {
                    val values: List<String>? = headers[headerName]

                    if (values != null) {
                        builder.addHeader(headerName, values)
                    }
                }
            }

            return builder.create()
        }
    }
}
