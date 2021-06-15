package com.sadri.universitypanel.domain.student.home.infrastructure

import com.sadri.universitypanel.domain.student.home.core.model.StudentCourseResponse
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.RequestStudentCourses
import com.sadri.universitypanel.domain.student.semester.core.model.StudentSemesterResponse
import com.sadri.universitypanel.domain.student.semester.core.model.StudentTakesRequest
import com.sadri.universitypanel.domain.student.semester.core.ports.outgoing.RequestSendStudentTakes
import com.sadri.universitypanel.domain.student.semester.core.ports.outgoing.RequestStudentSemester
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import com.sadri.universitypanel.infrastructure.utils.getResult

class StudentDataSourceImpl(
  private val studentDataSource: StudentDataSource
) : RequestStudentCourses, RequestStudentSemester, RequestSendStudentTakes {
  override suspend fun retrieveCourses(): ApiResult<List<StudentCourseResponse>> = getResult {
    studentDataSource.retrieveCourses()
  }

  override suspend fun retrieveSemester(): ApiResult<List<StudentSemesterResponse>> = getResult {
    studentDataSource.retrieveSemester()
  }

  override suspend fun send(request: StudentTakesRequest): ApiResult<Void> = getResult {
    studentDataSource.sendStudentTakes(request)
  }
}