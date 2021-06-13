package com.sadri.universitypanel.domain.instructor.home.infrastructure

import com.sadri.universitypanel.domain.instructor.home.core.model.InstructorSectionResponse
import com.sadri.universitypanel.domain.instructor.home.core.model.SectionStudentResponse
import com.sadri.universitypanel.domain.instructor.home.core.ports.outgoing.RequestInstructorSections
import com.sadri.universitypanel.domain.instructor.home.core.ports.outgoing.RequestSectionStudents
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import com.sadri.universitypanel.infrastructure.utils.getResult

class InstructorDataSourceImpl(
  private val instructorDataSource: InstructorDataSource
) : RequestInstructorSections, RequestSectionStudents {
  override suspend fun retrieveSections(): ApiResult<List<InstructorSectionResponse>> = getResult {
    instructorDataSource.retrieveSections()
  }

  override suspend fun retrieveStudents(sectionId: Int): ApiResult<List<SectionStudentResponse>> =
    getResult {
      instructorDataSource.retrieveStudents(sectionId)
    }
}