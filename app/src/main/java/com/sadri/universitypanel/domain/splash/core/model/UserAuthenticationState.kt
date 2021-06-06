package com.sadri.universitypanel.domain.splash.core.model

sealed class UserAuthenticationState {
  object NotAuthenticated : UserAuthenticationState()
  data class Authenticated(val token: String) : UserAuthenticationState()
}