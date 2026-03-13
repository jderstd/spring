package io.github.jderstd.spring.response.base

import org.springframework.http.HttpHeaders

/**
 * Create a base response.
 */
public open class CreateBaseResponse {
    /**
     * Response status code.
     */
    public var status: Int = 200

    /**
     * Response headers.
     */
    public var headers: HttpHeaders = HttpHeaders()

    /**
     * Add response header.
     */
    public fun addHeader(
        key: String,
        value: String,
    ) {
        this.headers.add(key, value)
    }

    /**
     * Add response header.
     */
    public fun addHeader(
        key: String,
        values: Iterable<String>,
    ) {
        this.headers.addAll(key, values.toList())
    }

    /**
     * Add response headers.
     *
     * @param keyValues a map of header names and values.
     * Ech value must be either a [String] or an [Iterable] of [String].
     */
    public fun addHeaders(keyValues: Map<String, Any>) {
        for ((key, value) in keyValues) {
            when (value) {
                is String -> {
                    this.headers.add(key, value)
                }

                is Iterable<*> -> {
                    this.headers.addAll(key, value.filterIsInstance<String>())
                }

                else -> {
                    throw IllegalArgumentException(
                        "Header '$key' must be a String or Iterable<String>, but was ${value::class.qualifiedName}",
                    )
                }
            }
        }
    }
}
