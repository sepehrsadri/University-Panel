package com.sadri.universitypanel.domain.instructor.home.core.ports.incoming

import com.sadri.universitypanel.domain.instructor.home.core.model.InstructorSectionResponse
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface RetrieveInstructorSections {
  suspend fun retrieveSections(): ApiResult<List<InstructorSectionResponse>>
}