package com.sadri.universitypanel.domain.instructor.home.core.ports.outgoing

import com.sadri.universitypanel.domain.instructor.home.core.model.SectionStudentResponse
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface RequestSectionStudents {
  suspend fun retrieveStudents(sectionId: Int): ApiResult<List<SectionStudentResponse>>
}