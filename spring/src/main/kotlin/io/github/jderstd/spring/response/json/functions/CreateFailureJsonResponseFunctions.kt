package io.github.jderstd.spring.response.json.functions

import io.github.jderstd.spring.response.json.JsonResponseError

public open class CreateFailureJsonResponseFunctions<T : Any> : CreateBaseJsonResponseFunctions<T>() {
    public fun addError(error: JsonResponseError) {
        this.json.errors.add(error)
    }

    public fun addErrors(errors: Iterable<JsonResponseError>) {
        this.json.errors.addAll(errors)
    }
}
