package com.sadri.universitypanel.domain.instructor.home.core.model

data class InstructorHomeViewState(
  val name: String = "",
  val sections: List<InstructorSectionResponse> = listOf()
)