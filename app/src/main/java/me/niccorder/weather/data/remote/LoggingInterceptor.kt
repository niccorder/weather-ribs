package me.niccorder.weather.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

object LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain!!.request()
        val response = chain.proceed(request)

        Timber.tag("Network").v(
                String.format(
                        "%s\t%s\t\t%d",
                        request.method(),
                        request.url(),
                        response.code()
                )
        )
        return response
    }
}