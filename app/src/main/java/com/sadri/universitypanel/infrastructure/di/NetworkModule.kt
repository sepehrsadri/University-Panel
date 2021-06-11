package com.sadri.universitypanel.infrastructure.di

import com.sadri.universitypanel.domain.login.core.ports.outgoing.SendAuthenticateUserRequest
import com.sadri.universitypanel.domain.login.infrastructure.AuthenticationDataSource
import com.sadri.universitypanel.domain.login.infrastructure.AuthenticationDataSourceImpl
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthProvider
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.GetCoursesRequest
import com.sadri.universitypanel.domain.student.home.infrastructure.StudentDataSource
import com.sadri.universitypanel.domain.student.home.infrastructure.StudentDataSourceImpl
import com.sadri.universitypanel.infrastructure.utils.AuthHeaderTokenInterceptor
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
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Provides
  @Singleton
  @Named("withoutToken")
  fun provideOkHttpClientWithoutToken(): OkHttpClient {
    return okHttpClientBuilder.build()
  }

  @Provides
  @Singleton
  fun provideAuthHeaderTokenInterceptor(userAuthProvider: UserAuthProvider): AuthHeaderTokenInterceptor {
    return AuthHeaderTokenInterceptor(userAuthProvider)
  }

  @Provides
  @Singleton
  @Named("withToken")
  fun provideOkHttpClientWithToken(authHeaderTokenInterceptor: AuthHeaderTokenInterceptor): OkHttpClient {
    return okHttpClientBuilder.addInterceptor(authHeaderTokenInterceptor).build()
  }

  @Provides
  @Singleton
  @Named("withoutToken")
  fun provideRetrofitWithoutToken(
    @Named("withoutToken") okHttpClient: OkHttpClient
  ): Retrofit {
    return getRetrofit(okHttpClient)
  }

  @Provides
  @Singleton
  @Named("withToken")
  fun provideRetrofitWithToken(
    @Named("withToken") okHttpClient: OkHttpClient
  ): Retrofit {
    return getRetrofit(okHttpClient)
  }

  @Provides
  @Singleton
  fun provideSendAuthenticateUserRequest(@Named("withoutToken") retrofit: Retrofit): SendAuthenticateUserRequest {
    return AuthenticationDataSourceImpl(
      provideService(retrofit, AuthenticationDataSource::class.java)
    )
  }

  @Provides
  @Singleton
  fun provideStudentDataSource(@Named("withToken") retrofit: Retrofit): StudentDataSource {
    return provideService(retrofit, StudentDataSource::class.java)
  }

  @Provides
  @Singleton
  fun provideGetCoursesRequest(studentDataSource: StudentDataSource): GetCoursesRequest {
    return StudentDataSourceImpl(studentDataSource)
  }

  private val okHttpClientBuilder by lazy {
    val httpClient = OkHttpClient.Builder()
      .readTimeout(25_000, TimeUnit.MILLISECONDS)
      .writeTimeout(25_000, TimeUnit.MILLISECONDS)
      .connectTimeout(25_000, TimeUnit.MILLISECONDS)

    getLoggingInterceptor().let { loggerInterceptor ->
      httpClient.addInterceptor(loggerInterceptor)
    }
  }

  private const val API_BASE_URL = "http://192.168.0.210:8080/api/"

  private fun getLoggingInterceptor(): Interceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BODY

    return logger
  }

  private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val builder = Retrofit.Builder()
      .baseUrl(
        API_BASE_URL
      ).addConverterFactory(
        GsonConverterFactory.create()
      )
      .client(
        okHttpClient
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