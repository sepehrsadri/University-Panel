package com.sadri.universitypanel.domain.student.home.infrastructure

import com.sadri.universitypanel.domain.student.home.core.model.StudentCourseResponse
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.RequestStudentCourses
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import com.sadri.universitypanel.infrastructure.utils.getResult

class StudentDataSourceImpl(
  private val studentDataSource: StudentDataSource
) : RequestStudentCourses {
  override suspend fun retrieveCourses(): ApiResult<List<StudentCourseResponse>> = getResult {
    studentDataSource.retrieveCourses()
  }
}