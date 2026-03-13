package io.github.jderstd.spring.response.json

import io.github.jderstd.spring.response.json.functions.CreateFailureJsonResponseFunctions
import io.github.jderstd.spring.response.json.functions.CreateSuccessJsonResponseFunctions

/**
 * Create a JSON response.
 */
public class CreateJsonResponse {
    public companion object {
        /**
         * Create a success JSON response without any data.
         */
        public fun dataless(): CreateSuccessJsonResponseFunctions<Unit> = CreateSuccessJsonResponseFunctions()

        /**
         * Create a success JSON response.
         */
        public fun <T : Any> success(): CreateSuccessJsonResponseFunctions<T> = CreateSuccessJsonResponseFunctions()

        /**
         * Create a failure JSON response.
         */
        public fun <T : Any> failure(): CreateFailureJsonResponseFunctions<T> = CreateFailureJsonResponseFunctions()
    }
}
