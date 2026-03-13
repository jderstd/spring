package io.github.jderstd.spring.response.json.functions

/**
 * Create a success JSON response.
 */
public open class CreateSuccessJsonResponseFunctions<Data : Any> :
    CreateBaseJsonResponseFunctions<Data, CreateSuccessJsonResponseFunctions<Data>>()
