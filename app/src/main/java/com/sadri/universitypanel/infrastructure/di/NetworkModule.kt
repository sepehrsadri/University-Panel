package com.sadri.universitypanel.infrastructure.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Provides
  @Singleton
  fun provideRetrofit(): Retrofit {
    return getRetrofit()
  }

  private const val TIMEOUT_SECONDS: Long = 15

  private val CLIENT by lazy {
    val httpClient = OkHttpClient.Builder()
      .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
      .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
      .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)

    getLoggingInterceptor().let {
      httpClient.addInterceptor(
        it
      )
    }

    httpClient.build()
  }

  private const val API_BASE_URL = "http://192.168.0.249:8080/api/"

  private fun getLoggingInterceptor(): Interceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BODY

    return logger
  }

  private fun getRetrofit(): Retrofit {
    val httpClient = OkHttpClient.Builder()
      .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
      .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
      .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)

    getLoggingInterceptor().let { interceptor ->
      httpClient.addInterceptor(
        interceptor
      )
    }

    val builder = Retrofit.Builder()
      .baseUrl(
        API_BASE_URL
      ).addConverterFactory(
        GsonConverterFactory.create()
      )
      .client(
        CLIENT
      )

    return builder.build()
  }

  fun <T> provideService(
    retrofit: Retrofit,
    clazz: Class<T>
  ): T {
    return retrofit.create(clazz)
  }
}

