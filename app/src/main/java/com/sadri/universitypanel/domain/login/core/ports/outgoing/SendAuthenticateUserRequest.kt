package com.sadri.universitypanel.domain.login.core.ports.outgoing

import com.sadri.universitypanel.domain.login.core.model.AuthenticateRequest
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import com.sadri.universitypanel.domain.login.core.model.UserRule
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface SendAuthenticateUserRequest {
  suspend fun authenticate(
    userRule: UserRule,
    authenticateRequest: AuthenticateRequest
  ): ApiResult<AuthenticationResponse>
}