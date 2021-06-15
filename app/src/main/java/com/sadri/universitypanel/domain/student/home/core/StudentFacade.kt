package com.sadri.universitypanel.domain.student.home.core

import com.sadri.universitypanel.domain.student.home.core.model.StudentCourseResponse
import com.sadri.universitypanel.domain.student.home.core.ports.incoming.RetrieveStudentCourses
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.RequestStudentCourses
import com.sadri.universitypanel.domain.student.semester.core.model.StudentSemesterResponse
import com.sadri.universitypanel.domain.student.semester.core.model.StudentTakesRequest
import com.sadri.universitypanel.domain.student.semester.core.ports.incoming.RetrieveStudentSemester
import com.sadri.universitypanel.domain.student.semester.core.ports.incoming.SubmitStudentTakes
import com.sadri.universitypanel.domain.student.semester.core.ports.outgoing.RequestSendStudentTakes
import com.sadri.universitypanel.domain.student.semester.core.ports.outgoing.RequestStudentSemester
import com.sadri.universitypanel.infrastructure.utils.ApiResult

class StudentFacade(
  private val requestStudentCourses: RequestStudentCourses,
  private val requestStudentSemester: RequestStudentSemester,
  private val requestSendStudentTakes: RequestSendStudentTakes
) : RetrieveStudentCourses, RetrieveStudentSemester, SubmitStudentTakes {

  override suspend fun retrieveCourses(): ApiResult<List<StudentCourseResponse>> {
    return requestStudentCourses.retrieveCourses()
  }

  override suspend fun retrieveSemester(): ApiResult<List<StudentSemesterResponse>> {
    return requestStudentSemester.retrieveSemester()
  }

  override suspend fun submit(request: StudentTakesRequest): ApiResult<Void> {
    return requestSendStudentTakes.send(request)
  }
}