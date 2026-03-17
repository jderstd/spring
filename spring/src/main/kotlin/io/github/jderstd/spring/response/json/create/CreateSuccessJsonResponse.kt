package io.github.jderstd.spring.response.json.create

/**
 * Create a success JSON response.
 */
public open class CreateSuccessJsonResponse<Data : Any> : CreateBaseJsonResponse<Data, CreateSuccessJsonResponse<Data>>() {
    init {
        this.status = 200
        this.json.success = true
    }

    @set:JvmSynthetic
    public var data: Data?
        get() = this.json.data
        set(data) {
            this.json.data = data
        }

    @JvmName("setData")
    public fun data(data: Data): CreateSuccessJsonResponse<Data> {
        this.json.data = data
        return self()
    }
}
