package com.bekarys.tech_assignment.thecats.features.common.api.cat

import okhttp3.Interceptor
import okhttp3.Response

class CatServiceInterceptor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("x-api-key", apiKey)
            .build()
        return chain.proceed(request)
    }
}