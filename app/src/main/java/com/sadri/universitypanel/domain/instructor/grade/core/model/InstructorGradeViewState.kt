package com.sadri.universitypanel.domain.instructor.grade.core.model

import com.sadri.universitypanel.domain.instructor.home.core.model.SectionStudentResponse

data class InstructorGradeViewState(
  val students: List<SectionStudentResponse> = listOf()
) {
  val isLoading: Boolean get() = students.isEmpty()
}