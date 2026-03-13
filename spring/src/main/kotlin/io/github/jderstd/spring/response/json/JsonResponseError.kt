package io.github.jderstd.spring.response.json

public open class JsonResponseError {
    public var code: String = ResponseError.UNKNOWN.message()

    public var path: List<String> = listOf<String>()

    public var message: String? = null
}
