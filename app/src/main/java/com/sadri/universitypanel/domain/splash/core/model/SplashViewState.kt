package com.sadri.universitypanel.domain.splash.core.model

data class SplashViewState(
  val userState: SplashUserState
)

enum class SplashUserState {
  LOADING, AUTHENTICATED, NOT_AUTHENTICATED
}