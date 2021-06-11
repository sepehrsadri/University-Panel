package com.sadri.universitypanel.domain.student.home.core.ports.outgoing

import com.sadri.universitypanel.domain.student.home.core.model.CourseResponse
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface GetCoursesRequest {
  suspend fun getCourses(): ApiResult<List<CourseResponse>>
}