package io.github.jderstd.spring.response.json

/**
 * Response error code.
 */
public enum class ResponseError {
    /**
     * Internal server error.
     */
    SERVER,

    /**
     * Unknown error.
     */
    UNKNOWN,
    ;

    /**
     * Get message of the error code.
     */
    public fun message(): String =
        when (this) {
            SERVER -> "Internal server error"
            UNKNOWN -> "Unknown error"
        }
}
