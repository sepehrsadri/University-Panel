package com.sadri.universitypanel.domain.student.home.core.ports.incoming

import com.sadri.universitypanel.domain.student.home.core.model.CourseResponse
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface GetStudentCourses {
  suspend fun retrieveCourses(): ApiResult<List<CourseResponse>>
}