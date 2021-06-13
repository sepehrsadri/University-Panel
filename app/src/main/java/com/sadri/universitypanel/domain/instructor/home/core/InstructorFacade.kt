package com.sadri.universitypanel.domain.instructor.home.core

import com.sadri.universitypanel.domain.instructor.home.core.model.InstructorSectionResponse
import com.sadri.universitypanel.domain.instructor.home.core.ports.incoming.RetrieveInstructorSections
import com.sadri.universitypanel.domain.instructor.home.core.ports.outgoing.RequestInstructorSections
import com.sadri.universitypanel.infrastructure.utils.ApiResult

class InstructorFacade(
  private val requestInstructorSections: RequestInstructorSections
) : RetrieveInstructorSections {
  override suspend fun retrieveSections(): ApiResult<List<InstructorSectionResponse>> {
    return requestInstructorSections.retrieveSections()
  }
}