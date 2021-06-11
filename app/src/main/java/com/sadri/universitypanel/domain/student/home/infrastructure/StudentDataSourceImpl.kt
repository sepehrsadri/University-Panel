package com.sadri.universitypanel.domain.student.home.infrastructure

import com.sadri.universitypanel.domain.student.home.core.model.CourseResponse
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.GetCoursesRequest
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import com.sadri.universitypanel.infrastructure.utils.getResult

class StudentDataSourceImpl(
  private val studentDataSource: StudentDataSource
) : GetCoursesRequest {
  override suspend fun getCourses(): ApiResult<List<CourseResponse>> = getResult {
    studentDataSource.getCourses()
  }
}