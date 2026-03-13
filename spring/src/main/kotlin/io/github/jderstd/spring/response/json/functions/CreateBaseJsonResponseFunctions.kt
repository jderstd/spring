package io.github.jderstd.spring.response.json.functions

import io.github.jderstd.spring.response.base.CreateBaseResponseFunctions
import io.github.jderstd.spring.response.json.JsonResponse
import org.springframework.http.ResponseEntity

/**
 * Create a base JSON response.
 */
public open class CreateBaseJsonResponseFunctions<Data : Any, Self : CreateBaseJsonResponseFunctions<Data, Self>> :
    CreateBaseResponseFunctions<Self>() {
    /**
     * JSON body.
     */
    public var json: JsonResponse<Data> = JsonResponse()

    /**
     * Set JSON body.
     */
    public fun json(json: JsonResponse<Data>): Self {
        this.json = json
        return self()
    }

    /**
     * Finish the response creation.
     */
    public fun create(): ResponseEntity<JsonResponse<Data>> {
        this.headers.set("Content-Type", "application/json")

        return ResponseEntity
            .status(this.status)
            .headers(this.headers)
            .body(this.json)
    }
}
