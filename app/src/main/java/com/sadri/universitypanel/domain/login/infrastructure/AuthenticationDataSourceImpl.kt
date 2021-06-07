package com.sadri.universitypanel.domain.login.infrastructure

import com.sadri.universitypanel.domain.login.core.model.AuthenticateRequest
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import com.sadri.universitypanel.domain.login.core.model.UserRule
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SendAuthenticateUserRequest
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import com.sadri.universitypanel.infrastructure.utils.getResult

class AuthenticationDataSourceImpl(
  private val authenticationDataSource: AuthenticationDataSource
) : SendAuthenticateUserRequest {
  override suspend fun authenticate(
    userRule: UserRule,
    authenticateRequest: AuthenticateRequest
  ): ApiResult<AuthenticationResponse> = getResult {
    when (userRule) {
      UserRule.STUDENT -> authenticationDataSource.authenticateStudent(authenticateRequest)
      UserRule.MASTER -> authenticationDataSource.authenticateMaster(authenticateRequest)
    }
  }
}