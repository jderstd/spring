package io.github.jderstd.spring.response.json.functions

/**
 * Create a success JSON response.
 */
public open class CreateSuccessJsonResponseFunctions<Data : Any> :
    CreateBaseJsonResponseFunctions<Data, CreateSuccessJsonResponseFunctions<Data>>() {
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
    public fun data(data: Data): CreateSuccessJsonResponseFunctions<Data> {
        this.json.data = data
        return self()
    }
}
