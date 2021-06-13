package com.sadri.universitypanel.domain.instructor.home.core.ports.outgoing

import com.sadri.universitypanel.domain.instructor.home.core.model.InstructorSectionResponse
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface RequestInstructorSections {
  suspend fun retrieveSections(): ApiResult<List<InstructorSectionResponse>>
}