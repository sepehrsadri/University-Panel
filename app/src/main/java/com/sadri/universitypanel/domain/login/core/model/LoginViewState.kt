package com.sadri.universitypanel.domain.login.core.model

data class LoginViewState(
  val number: String = "10101",
  val password: String = "admin",
  val rule: UserRule = UserRule.INSTRUCTOR
)