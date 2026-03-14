package io.github.jderstd.spring.response.json

/**
 * Response error code.
 */
public enum class ResponseError {
    /**
     * Content not found.
     */
    NOT_FOUND,

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
     * Get HTTP status of the error.
     */
    @JvmName("getStatus")
    public fun status(): Int =
        when (this) {
            NOT_FOUND -> 404
            SERVER -> 500
            UNKNOWN -> 500
        }

    /**
     * Get code of the error.
     */
    @JvmName("getCode")
    public fun code(): String =
        when (this) {
            NOT_FOUND -> "not_found"
            SERVER -> "server"
            UNKNOWN -> "unknown"
        }

    /**
     * Get message of the error.
     */
    @JvmName("getMessage")
    public fun message(): String =
        when (this) {
            NOT_FOUND -> "Content not found"
            SERVER -> "Internal server error"
            UNKNOWN -> "Unknown error"
        }
}
