package com.sadri.universitypanel.domain.login.core.ports.outgoing

import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import com.sadri.universitypanel.domain.login.core.model.UserRule

interface SaveUserAuthenticationDatabase {
  suspend fun handle(
    userRule: UserRule,
    authenticationResponse: AuthenticationResponse?,
    number : String,
  )
}