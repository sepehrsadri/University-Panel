package com.sadri.universitypanel.domain.student.home.core.model

data class StudentHomeViewState(
  val courses: List<StudentCourseResponse> = listOf(),
  val isLoading: Boolean = true
)