package com.sadri.universitypanel.domain.login.core

import com.sadri.universitypanel.domain.login.core.model.AuthenticateRequest
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import com.sadri.universitypanel.domain.login.core.model.UserRule
import com.sadri.universitypanel.domain.login.core.ports.incoming.OnUserAuthenticate
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SaveUserAuthenticationDatabase
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SendAuthenticateUserRequest
import com.sadri.universitypanel.domain.splash.core.ports.incoming.AuthStateChanged
import com.sadri.universitypanel.domain.splash.core.ports.incoming.RequestLogout
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.ClearUserInfoDatabase
import com.sadri.universitypanel.infrastructure.utils.ApiResult

class LoginFacade(
  private val sendAuthenticateUserRequest: SendAuthenticateUserRequest,
  private val saveUserAuthenticationDatabase: SaveUserAuthenticationDatabase,
  private val clearUserInfoDatabase: ClearUserInfoDatabase,
  private val authStateChanged: AuthStateChanged
) : OnUserAuthenticate, RequestLogout {
  override suspend fun handle(
    userRule: UserRule,
    authenticateRequest: AuthenticateRequest
  ): ApiResult<AuthenticationResponse> {
    val authenticationResponse =
      sendAuthenticateUserRequest.authenticate(
        userRule = userRule,
        authenticateRequest = authenticateRequest
      )

    if (authenticationResponse is ApiResult.Success) {
      saveUserAuthenticationDatabase.handle(
        userRule = userRule,
        authenticationResponse = authenticationResponse.data,
        authenticateRequest.number
      )
      authStateChanged.handle(authenticationResponse.data?.token)
    }

    return authenticationResponse
  }

  override suspend fun logout() {
    clearUserInfoDatabase.clear()
  }
}