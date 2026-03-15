package io.github.jderstd.spring.response.json

/**
 * JSON response.
 */
public open class JsonResponse<Data : Any> {
    /**
     * Indicates whether the response is successful or not.
     */
    @set:JvmSynthetic
    public var success: Boolean = true

    /**
     * Requested information for the response when `success` is `true`.
     */
    @set:JvmSynthetic
    public var data: Data? = null

    /**
     * A list of errors for the response when `success` is `false`.
     */
    @set:JvmSynthetic
    public var errors: MutableList<JsonResponseError> = mutableListOf<JsonResponseError>()
        set(errors) {
            field = errors.toMutableList()
        }

    /**
     * Set whether the response is successful or not.
     */
    @JvmName("setSuccess")
    public fun success(success: Boolean): JsonResponse<Data> {
        this.success = success
        return this
    }

    /**
     * Set requested information for the response.
     */
    @JvmName("setData")
    public fun data(data: Data?): JsonResponse<Data> {
        this.data = data
        return this
    }

    /**
     * Get the first error for the response when `success` is `false`.
     */
    @JvmName("getError")
    public fun error(): JsonResponseError? = this.errors.firstOrNull()

    /**
     * Set errors for the response.
     */
    @JvmName("setErrors")
    public fun errors(errors: List<JsonResponseError>): JsonResponse<Data> {
        this.errors = errors.toMutableList()
        return this
    }

    /**
     * Add an error to the response.
     */
    public fun addError(error: JsonResponseError): JsonResponse<Data> {
        this.errors.add(error)
        return this
    }

    /**
     * Add a list of errors to the response.
     */
    public fun addErrors(errors: Iterable<JsonResponseError>): JsonResponse<Data> {
        this.errors.addAll(errors)
        return this
    }
}
