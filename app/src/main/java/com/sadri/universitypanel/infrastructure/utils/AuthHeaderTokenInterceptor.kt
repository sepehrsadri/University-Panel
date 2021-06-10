package com.sadri.universitypanel.infrastructure.utils

import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthProvider
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthHeaderTokenInterceptor(
  private val userAuthProvider: UserAuthProvider
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    return if (userAuthProvider.isLoggedIn) {
      chain.proceed(chain.request().addAuthHeader())
    } else
      chain.proceed(chain.request())
  }

  private fun Request.addAuthHeader(): Request {
    return this.newBuilder()
      .header("Authorization", "Bearer ${userAuthProvider.retrieveToken()}")
      .build()
  }
}