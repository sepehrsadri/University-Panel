package com.sadri.universitypanel.domain.student.home.core.ports.outgoing

import com.sadri.universitypanel.domain.student.home.core.model.StudentCourseResponse
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface RequestStudentCourses {
  suspend fun retrieveCourses(): ApiResult<List<StudentCourseResponse>>
}