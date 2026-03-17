package io.github.jderstd.spring.response.json

import io.github.jderstd.spring.response.json.create.CreateFailureJsonResponse
import io.github.jderstd.spring.response.json.create.CreateSuccessJsonResponse

/**
 * Create a JSON response.
 */
public class CreateJsonResponse {
    public companion object {
        /**
         * Create a success JSON response without any data.
         */
        @JvmSynthetic
        @JvmName("_dataless")
        public fun dataless(): CreateSuccessJsonResponse<Unit> = CreateSuccessJsonResponse()

        /**
         * Create a success JSON response without any data.
         */
        @JvmStatic
        @JvmOverloads
        public fun dataless(
            @Suppress("UNUSED_PARAMETER") unused: Void? = null,
        ): CreateSuccessJsonResponse<Void> = CreateSuccessJsonResponse()

        /**
         * Create a success JSON response.
         */
        @JvmStatic
        public fun <T : Any> success(): CreateSuccessJsonResponse<T> = CreateSuccessJsonResponse()

        /**
         * Create a failure JSON response.
         */
        @JvmSynthetic
        @JvmName("_failure")
        public fun failure(): CreateFailureJsonResponse<Unit> = CreateFailureJsonResponse()

        /**
         * Create a failure JSON response.
         */
        @JvmStatic
        @JvmOverloads
        public fun failure(
            @Suppress("UNUSED_PARAMETER") unused: Void? = null,
        ): CreateFailureJsonResponse<Void> = CreateFailureJsonResponse()
    }
}
