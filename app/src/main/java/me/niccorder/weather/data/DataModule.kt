package me.niccorder.weather.data

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import me.niccorder.weather.data.remote.LoggingInterceptor
import me.niccorder.weather.internal.AppScope
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule {

    @AppScope
    @Provides
    fun provideGson(): Gson = Gson()

    @AppScope
    @Provides
    fun provideNetworkCache(context: Context): Cache = Cache(context.cacheDir, 10 * 1024 * 1024)

    @AppScope
    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory
            = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    @AppScope
    @Provides
    fun provideCallConverterFactory(
            gson: Gson
    ): Converter.Factory = GsonConverterFactory.create(gson)

    @AppScope
    @Provides
    fun providesOkhttpClientBuilder(
            gson: Gson,
            cache: Cache
    ): OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor)
            .retryOnConnectionFailure(true)

    @AppScope
    @Provides
    fun provideRetrofitBuilder(
            converterFactory: Converter.Factory,
            adapterFactory: CallAdapter.Factory
    ): Retrofit.Builder = Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(adapterFactory)
            .validateEagerly(true)
}