package com.sadri.universitypanel.domain.splash.core.model

data class SplashViewState(
  val userState: SplashUserState
)

enum class SplashUserState {
  LOADING,
  AUTHENTICATED_STUDENT,
  AUTHENTICATED_MASTER,
  NOT_AUTHENTICATED
}