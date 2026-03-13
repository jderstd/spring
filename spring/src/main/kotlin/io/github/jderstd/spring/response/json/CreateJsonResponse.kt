package io.github.jderstd.spring.response.json

import io.github.jderstd.spring.response.json.functions.CreateFailureJsonResponseFunctions
import io.github.jderstd.spring.response.json.functions.CreateSuccessJsonResponseFunctions

public class CreateJsonResponse {
    public companion object {
        public fun dataless(): CreateSuccessJsonResponseFunctions<Unit> = CreateSuccessJsonResponseFunctions()

        public fun <T : Any> success(): CreateSuccessJsonResponseFunctions<T> = CreateSuccessJsonResponseFunctions()

        public fun <T : Any> failure(): CreateFailureJsonResponseFunctions<T> = CreateFailureJsonResponseFunctions()
    }
}
