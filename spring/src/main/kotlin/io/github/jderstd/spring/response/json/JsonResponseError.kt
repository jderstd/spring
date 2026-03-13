package io.github.jderstd.spring.response.json

/**
 * JSON response error.
 */
public open class JsonResponseError {
    /**
     * Code representing the error.
     */
    public var code: String = ResponseError.UNKNOWN.message()

    /**
     * Indicates where the error occurred.
     */
    public var path: List<String> = listOf<String>()

    /**
     * Detail of the error.
     */
    public var message: String? = null

    /**
     * Set code representing the error.
     */
    public fun code(code: String): JsonResponseError {
        this.code = code
        return this
    }

    /**
     * Set where the error occurred.
     */
    public fun path(path: List<String>): JsonResponseError {
        this.path = path
        return this
    }

    /**
     * Set detail of the error.
     */
    public fun message(message: String?): JsonResponseError {
        this.message = message
        return this
    }
}
