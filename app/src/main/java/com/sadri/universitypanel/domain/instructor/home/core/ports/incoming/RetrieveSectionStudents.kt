package com.sadri.universitypanel.domain.instructor.home.core.ports.incoming

import com.sadri.universitypanel.domain.instructor.home.core.model.SectionStudentResponse
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface RetrieveSectionStudents {
  suspend fun retrieveStudents(sectionId: Int): ApiResult<List<SectionStudentResponse>>
}