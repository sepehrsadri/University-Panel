package com.sadri.universitypanel.domain.login.core.model

data class LoginViewState(
  val number: String = "45565",
  val password: String = "admin",
  val rule: UserRule = UserRule.INSTRUCTOR
)