package com.sadri.universitypanel.domain.login.core.ports.incoming

import com.sadri.universitypanel.domain.login.core.model.AuthenticateRequest
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface OnUserAuthenticate {
  suspend fun handle(authenticateRequest: AuthenticateRequest): ApiResult<AuthenticationResponse>
}