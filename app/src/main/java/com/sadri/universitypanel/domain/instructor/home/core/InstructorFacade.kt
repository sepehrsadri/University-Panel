package com.sadri.universitypanel.domain.instructor.home.core

import com.sadri.universitypanel.domain.instructor.grade.core.model.StudentGradeRequest
import com.sadri.universitypanel.domain.instructor.grade.core.ports.incoming.SubmitStudentsGrade
import com.sadri.universitypanel.domain.instructor.grade.core.ports.outgoing.RequestSendStudentsGrade
import com.sadri.universitypanel.domain.instructor.home.core.model.InstructorSectionResponse
import com.sadri.universitypanel.domain.instructor.home.core.model.SectionStudentResponse
import com.sadri.universitypanel.domain.instructor.home.core.ports.incoming.RetrieveInstructorSections
import com.sadri.universitypanel.domain.instructor.home.core.ports.incoming.RetrieveSectionStudents
import com.sadri.universitypanel.domain.instructor.home.core.ports.outgoing.RequestInstructorSections
import com.sadri.universitypanel.domain.instructor.home.core.ports.outgoing.RequestSectionStudents
import com.sadri.universitypanel.infrastructure.utils.ApiResult

class InstructorFacade(
  private val requestInstructorSections: RequestInstructorSections,
  private val requestSectionStudents: RequestSectionStudents,
  private val requestSendStudentsGrade: RequestSendStudentsGrade
) : RetrieveInstructorSections, RetrieveSectionStudents, SubmitStudentsGrade {
  override suspend fun retrieveSections(): ApiResult<List<InstructorSectionResponse>> {
    return requestInstructorSections.retrieveSections()
  }

  override suspend fun retrieveStudents(sectionId: Int): ApiResult<List<SectionStudentResponse>> {
    return requestSectionStudents.retrieveStudents(sectionId)
  }

  override suspend fun submit(request: StudentGradeRequest): ApiResult<Void> {
    return requestSendStudentsGrade.send(request)
  }
}