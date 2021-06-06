package com.sadri.universitypanel.domain.login.infrastructure

import com.sadri.universitypanel.domain.login.core.model.AuthenticateRequest
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationDataSource {
  @POST("user/login")
  suspend fun authenticate(
    @Body authenticationRequest: AuthenticateRequest
  ): Response<AuthenticationResponse>
}