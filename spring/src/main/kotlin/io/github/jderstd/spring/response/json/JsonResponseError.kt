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
}
