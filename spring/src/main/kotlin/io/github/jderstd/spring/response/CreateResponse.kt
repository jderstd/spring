package io.github.jderstd.spring.response

import io.github.jderstd.spring.response.base.CreateBaseResponse
import org.springframework.http.ResponseEntity

/**
 * Create a response.
 */
public open class CreateResponse<T : Any> : CreateBaseResponse() {
    /**
     * response body.
     */
    public var body: T? = null

    /**
     * Finish the response creation.
     */
    public fun create(): ResponseEntity<T> =
        ResponseEntity
            .status(this.status)
            .headers(this.headers)
            .body(this.body)
}
