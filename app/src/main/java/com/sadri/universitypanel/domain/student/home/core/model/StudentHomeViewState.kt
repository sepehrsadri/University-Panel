package com.sadri.universitypanel.domain.student.home.core.model

data class StudentHomeViewState(
  val name: String = "",
  val courses: List<CourseResponse> = listOf(),
  val isLoading: Boolean = true
)