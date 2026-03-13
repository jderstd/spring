package io.github.jderstd.spring.response.json.functions

import io.github.jderstd.spring.response.base.CreateBaseResponse
import io.github.jderstd.spring.response.json.JsonResponse
import org.springframework.http.ResponseEntity

public open class CreateBaseJsonResponseFunctions<T : Any> : CreateBaseResponse() {
    public var json: JsonResponse<T> = JsonResponse()

    public fun create(): ResponseEntity<JsonResponse<T>> {
        this.addHeader(
            "Content-Type",
            "application/json",
        )

        return ResponseEntity
            .status(this.status)
            .headers(this.headers)
            .body(this.json)
    }
}
