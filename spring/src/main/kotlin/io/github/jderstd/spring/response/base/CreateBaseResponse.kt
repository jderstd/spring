package io.github.jderstd.spring.response.base

import org.springframework.http.HttpHeaders

/**
 * Create a base response.
 */
public open class CreateBaseResponseFunctions<Self : CreateBaseResponseFunctions<Self>> {
    /**
     * Response status code.
     */
    @set:JvmSynthetic
    public var status: Int = 200

    /**
     * Response headers.
     */
    @set:JvmSynthetic
    public var headers: HttpHeaders = HttpHeaders()

    @Suppress("UNCHECKED_CAST")
    protected fun self(): Self = this as Self

    /**
     * Set response status code.
     */
    @JvmName("setStatus")
    public fun status(status: Int): Self {
        this.status = status
        return self()
    }

    /**
     * Set response headers.
     */
    @JvmName("setHeaders")
    public fun headers(httpHeaders: HttpHeaders): Self {
        this.headers = httpHeaders
        return self()
    }

    /**
     * Add response header.
     */
    public fun addHeader(
        key: String,
        value: String,
    ): Self {
        this.headers.add(key, value)
        return self()
    }

    /**
     * Add response header.
     */
    public fun addHeader(
        key: String,
        values: Iterable<String>,
    ): Self {
        this.headers.addAll(key, values.toList())
        return self()
    }

    /**
     * Add response headers.
     *
     * @param keyValues a map of header names and values.
     * Ech value must be either a [String] or an [Iterable] of [String].
     */
    public fun addHeaders(keyValues: Map<String, Any>): Self {
        for ((key, value) in keyValues) {
            when (value) {
                is String -> {
                    this.headers.add(key, value)
                }

                is Iterable<*> -> {
                    val headerValues: MutableList<String> = mutableListOf<String>()

                    for (headerValue: Any? in value) {
                        if (headerValue !is String) {
                            throw IllegalArgumentException(
                                "Header '$key' must be a String or Iterable<String>, but contained ${headerValue?.let {
                                    it::class
                                        .qualifiedName
                                } ?: "null"}",
                            )
                        }

                        headerValues.add(headerValue)
                    }

                    this.headers.addAll(key, headerValues)
                }

                else -> {
                    throw IllegalArgumentException(
                        "Header '$key' must be a String or Iterable<String>, but was ${value::class.qualifiedName}",
                    )
                }
            }
        }

        return self()
    }
}

/**
 * Create a base response.
 */
public open class CreateBaseResponse : CreateBaseResponseFunctions<CreateBaseResponse>()
