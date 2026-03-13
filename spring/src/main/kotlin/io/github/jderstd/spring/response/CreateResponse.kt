package io.github.jderstd.spring.response

import io.github.jderstd.spring.response.base.CreateBaseResponse
import org.springframework.http.ResponseEntity

public open class CreateResponse<T : Any> : CreateBaseResponse() {
    public var body: T? = null

    public fun create(): ResponseEntity<T> =
        ResponseEntity
            .status(this.status)
            .headers(this.headers)
            .body(this.body)
}
