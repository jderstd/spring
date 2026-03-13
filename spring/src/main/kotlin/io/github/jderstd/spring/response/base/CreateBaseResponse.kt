package io.github.jderstd.spring.response.base

import org.springframework.http.HttpHeaders

public open class CreateBaseResponse {
    public var status: Int = 200

    public var headers: HttpHeaders = HttpHeaders()

    public fun addHeader(
        key: String,
        value: String,
    ) {
        this.headers.add(key, value)
    }

    public fun addHeader(
        key: String,
        values: Iterable<String>,
    ) {
        this.headers.addAll(key, values.toList())
    }

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
