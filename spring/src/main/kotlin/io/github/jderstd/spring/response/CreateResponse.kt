package io.github.jderstd.spring.response

import io.github.jderstd.spring.response.base.CreateBaseResponseFunctions
import org.springframework.http.ResponseEntity

/**
 * Create a response.
 */
public open class CreateResponse<Body : Any> : CreateBaseResponseFunctions<CreateResponse<Body>>() {
    /**
     * response body.
     */
    @set:JvmSynthetic
    public var body: Body? = null

    /**
     * Set response body.
     */
    @JvmName("setBody")
    public fun body(body: Body?): CreateResponse<Body> {
        this.body = body
        return self()
    }

    /**
     * Finish the response creation.
     */
    public fun create(): ResponseEntity<Body> =
        ResponseEntity
            .status(this.status)
            .headers(this.headers)
            .body(this.body)
}
