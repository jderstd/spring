package io.github.jderstd.spring.response.json

public enum class ResponseError {
    UNKNOWN,
    ;

    public fun status(): Int =
        when (this) {
            UNKNOWN -> 500
        }

    public fun message(): String =
        when (this) {
            UNKNOWN -> "Unknown error"
        }
}
