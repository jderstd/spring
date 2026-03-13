package io.github.jderstd.spring.response.json.functions

import io.github.jderstd.spring.response.json.JsonResponseError

/**
 * Create a failure JSON response.
 */
public open class CreateFailureJsonResponseFunctions<T : Any> : CreateBaseJsonResponseFunctions<T>() {
    /**
     * Add an error to the response.
     */
    public fun addError(error: JsonResponseError) {
        this.json.errors.add(error)
    }

    /**
     * Add a list of errors to the response.
     */
    public fun addErrors(errors: Iterable<JsonResponseError>) {
        this.json.errors.addAll(errors)
    }
}
