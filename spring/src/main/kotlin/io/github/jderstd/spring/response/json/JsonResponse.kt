package io.github.jderstd.spring.response.json

public open class JsonResponse<T : Any> {
    public var success: Boolean = true

    public var data: T? = null

    public var errors: MutableList<JsonResponseError> = mutableListOf<JsonResponseError>()

    public fun error(): JsonResponseError = this.errors[0]

    public fun addError(error: JsonResponseError) {
        this.errors.add(error)
    }

    public fun addErrors(errors: Iterable<JsonResponseError>) {
        this.errors.addAll(errors)
    }
}
