package com.sadri.universitypanel.domain.student.home.core.ports.incoming

import com.sadri.universitypanel.domain.student.home.core.model.StudentCourseResponse
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface RetrieveStudentCourses {
  suspend fun retrieveCourses(): ApiResult<List<StudentCourseResponse>>
}