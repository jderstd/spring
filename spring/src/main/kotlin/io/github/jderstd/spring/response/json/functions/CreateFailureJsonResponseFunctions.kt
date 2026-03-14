package io.github.jderstd.spring.response.json.functions

import io.github.jderstd.spring.response.json.JsonResponseError

/**
 * Create a failure JSON response.
 */
public open class CreateFailureJsonResponseFunctions<Data : Any> :
    CreateBaseJsonResponseFunctions<Data, CreateFailureJsonResponseFunctions<Data>>() {
    init {
        this.status = 400
        this.json.success = false
    }

    @set:JvmSynthetic
    public var errors: List<JsonResponseError>
        get() = this.json.errors
        set(errors) {
            this.json.errors = errors.toMutableList()
        }

    @JvmName("setErrors")
    public fun errors(errors: List<JsonResponseError>): CreateFailureJsonResponseFunctions<Data> {
        this.json.errors = errors.toMutableList()
        return self()
    }

    /**
     * Add an error to the response.
     */
    public fun addError(error: JsonResponseError): CreateFailureJsonResponseFunctions<Data> {
        this.json.errors.add(error)
        return self()
    }

    /**
     * Add a list of errors to the response.
     */
    public fun addErrors(errors: Iterable<JsonResponseError>): CreateFailureJsonResponseFunctions<Data> {
        this.json.errors.addAll(errors)
        return self()
    }
}
