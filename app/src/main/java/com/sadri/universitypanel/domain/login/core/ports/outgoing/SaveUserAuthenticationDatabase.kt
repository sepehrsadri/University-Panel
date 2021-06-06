package com.sadri.universitypanel.domain.login.core.ports.outgoing

import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse

interface SaveUserAuthenticationDatabase {
  suspend fun handle(authenticationResponse: AuthenticationResponse?)
}