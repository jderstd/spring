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
        @JvmSynthetic
        @JvmName("datalessUnit")
        public fun dataless(): CreateSuccessJsonResponseFunctions<Unit> = CreateSuccessJsonResponseFunctions()

        /**
         * Create a success JSON response without any data.
         */
        @JvmStatic
        @JvmOverloads
        public fun dataless(
            @Suppress("UNUSED_PARAMETER") unused: Void? = null,
        ): CreateSuccessJsonResponseFunctions<Void> = CreateSuccessJsonResponseFunctions()

        /**
         * Create a success JSON response.
         */
        @JvmStatic
        public fun <T : Any> success(): CreateSuccessJsonResponseFunctions<T> = CreateSuccessJsonResponseFunctions()

        /**
         * Create a failure JSON response.
         */
        @JvmSynthetic
        @JvmName("failureUnit")
        public fun failure(): CreateFailureJsonResponseFunctions<Unit> = CreateFailureJsonResponseFunctions()

        /**
         * Create a failure JSON response.
         */
        @JvmStatic
        @JvmOverloads
        public fun failure(
            @Suppress("UNUSED_PARAMETER") unused: Void? = null,
        ): CreateFailureJsonResponseFunctions<Void> = CreateFailureJsonResponseFunctions()
    }
}
