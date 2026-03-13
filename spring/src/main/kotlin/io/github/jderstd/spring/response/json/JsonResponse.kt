package io.github.jderstd.spring.response.json

/**
 * JSON response.
 */
public open class JsonResponse<T : Any> {
    /**
     * Indicates whether the response is successful or not.
     */
    public var success: Boolean = true

    /**
     * Requested information for the response when `success` is `true`.
     */
    public var data: T? = null

    /**
     * A list of errors for the response when `success` is `false`.
     */
    public var errors: MutableList<JsonResponseError> = mutableListOf<JsonResponseError>()

    /**
     * Get the first error for the response when `success` is `false`.
     */
    public fun error(): JsonResponseError = this.errors[0]

    /**
     * Add an error to the response.
     */
    public fun addError(error: JsonResponseError) {
        this.errors.add(error)
    }

    /**
     * Add a list of errors to the response.
     */
    public fun addErrors(errors: Iterable<JsonResponseError>) {
        this.errors.addAll(errors)
    }
}
