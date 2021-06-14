package com.sadri.universitypanel.domain.student.profile.core.model

data class StudentProfileViewState(
  val name: String = "",
  val number: String = "",
  val isLoading: Boolean = true
)