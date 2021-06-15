package com.sadri.universitypanel.domain.student.semester.core.model

data class StudentSemesterViewState(
  val takes: List<StudentSemesterResponse> = listOf()
) {
  val isLoading: Boolean get() = takes.isEmpty()
}