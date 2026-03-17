package io.github.jderstd.spring.response.json.create

import io.github.jderstd.spring.response.base.CreateBaseResponseFunctions
import io.github.jderstd.spring.response.json.JsonResponse
import org.springframework.http.ResponseEntity

/**
 * Create a base JSON response.
 */
public open class CreateBaseJsonResponse<Data : Any, Self : CreateBaseJsonResponse<Data, Self>> : CreateBaseResponseFunctions<Self>() {
    /**
     * JSON body.
     */
    @set:JvmSynthetic
    protected var json: JsonResponse<Data> = JsonResponse()

    /**
     * Set JSON body.
     */
    @JvmName("setJson")
    protected fun json(json: JsonResponse<Data>): Self {
        this.json = json
        return self()
    }

    private fun snapshotJsonResponse(source: JsonResponse<Data>): JsonResponse<Data> =
        JsonResponse<Data>()
            .success(source.success)
            .data(source.data)
            .errors(source.errors.toList())

    /**
     * Finish the response creation.
     */
    public fun create(): ResponseEntity<JsonResponse<Data>> {
        this.headers.set("Content-Type", "application/json")

        return ResponseEntity
            .status(this.status)
            .headers(this.headers)
            .body(snapshotJsonResponse(this.json))
    }
}
