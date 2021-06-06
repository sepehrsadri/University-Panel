package com.sadri.universitypanel.domain.login.infrastructure

import com.sadri.universitypanel.domain.login.core.model.AuthenticateRequest
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SendAuthenticateUserRequest
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import com.sadri.universitypanel.infrastructure.utils.getResult

class AuthenticationDataSourceImpl(
  private val authenticationDataSource: AuthenticationDataSource
) : SendAuthenticateUserRequest {
  override suspend fun authenticate(authenticateRequest: AuthenticateRequest): ApiResult<AuthenticationResponse> =
    getResult {
      authenticationDataSource.authenticate(authenticateRequest)
    }
}