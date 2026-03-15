package io.github.jderstd.spring.response.json

/**
 * JSON response error.
 */
public open class JsonResponseError {
    /**
     * Code representing the error.
     */
    @set:JvmSynthetic
    public var code: String = ResponseError.UNKNOWN.code()

    /**
     * Indicates where the error occurred.
     */
    @set:JvmSynthetic
    public var path: List<String> = listOf<String>()
        set(path) {
            field = path.toList()
        }

    /**
     * Detail of the error.
     */
    @set:JvmSynthetic
    public var message: String? = null

    /**
     * Set code representing the error.
     */
    @JvmName("setCode")
    public fun code(code: String): JsonResponseError {
        this.code = code
        return this
    }

    /**
     * Set where the error occurred.
     */
    @JvmName("setPath")
    public fun path(path: List<String>): JsonResponseError {
        this.path = path
        return this
    }

    /**
     * Set detail of the error.
     */
    @JvmName("setMessage")
    public fun message(message: String?): JsonResponseError {
        this.message = message
        return this
    }
}
