package com.sadri.universitypanel.domain.login.core

import com.sadri.universitypanel.domain.login.core.model.AuthenticateRequest
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import com.sadri.universitypanel.domain.login.core.ports.incoming.OnUserAuthenticate
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SaveUserAuthenticationDatabase
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SendAuthenticateUserRequest
import com.sadri.universitypanel.infrastructure.utils.ApiResult

class LoginFacade(
  private val sendAuthenticateUserRequest: SendAuthenticateUserRequest,
  private val saveUserAuthenticationDatabase: SaveUserAuthenticationDatabase
) : OnUserAuthenticate {
  override suspend fun handle(authenticateRequest: AuthenticateRequest): ApiResult<AuthenticationResponse> {
    val authenticationResponse = sendAuthenticateUserRequest.authenticate(authenticateRequest)

    if (authenticationResponse is ApiResult.Success) {
      saveUserAuthenticationDatabase.handle(authenticationResponse.data)
    }

    return authenticationResponse
  }
}