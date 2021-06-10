package com.sadri.universitypanel.domain.login.infrastructure

import com.sadri.universitypanel.domain.login.core.model.AuthenticateRequest
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationDataSource {
  @POST("auth/student")
  suspend fun authenticateStudent(
    @Body authenticationRequest: AuthenticateRequest
  ): Response<AuthenticationResponse>

  @POST("auth/instructor")
  suspend fun authenticateMaster(
    @Body authenticationRequest: AuthenticateRequest
  ): Response<AuthenticationResponse>
}