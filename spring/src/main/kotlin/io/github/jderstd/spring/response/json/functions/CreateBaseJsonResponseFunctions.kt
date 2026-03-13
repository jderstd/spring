package io.github.jderstd.spring.response.json.functions

import io.github.jderstd.spring.response.base.CreateBaseResponse
import io.github.jderstd.spring.response.json.JsonResponse
import org.springframework.http.ResponseEntity

/**
 * Create a base JSON response.
 */
public open class CreateBaseJsonResponseFunctions<T : Any> : CreateBaseResponse() {
    /**
     * JSON body.
     */
    public var json: JsonResponse<T> = JsonResponse()

    /**
     * Finish the response creation.
     */
    public fun create(): ResponseEntity<JsonResponse<T>> {
        this.headers.set("Content-Type", "application/json")

        return ResponseEntity
            .status(this.status)
            .headers(this.headers)
            .body(this.json)
    }
}
