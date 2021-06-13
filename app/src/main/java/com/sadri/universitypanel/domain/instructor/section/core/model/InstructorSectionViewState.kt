package com.sadri.universitypanel.domain.instructor.section.core.model

import com.sadri.universitypanel.domain.instructor.home.core.model.SectionStudentResponse

data class InstructorSectionViewState(
  val students: List<SectionStudentResponse> = listOf(),
  val isLoading: Boolean = true
)