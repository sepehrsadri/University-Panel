package com.sadri.universitypanel.domain.student.home.core

import com.sadri.universitypanel.domain.student.home.core.model.StudentCourseResponse
import com.sadri.universitypanel.domain.student.home.core.ports.incoming.RetrieveStudentCourses
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.RequestStudentCourses
import com.sadri.universitypanel.infrastructure.utils.ApiResult

class StudentFacade(
  private val requestStudentCourses: RequestStudentCourses
) :  RetrieveStudentCourses {

  override suspend fun retrieveCourses(): ApiResult<List<StudentCourseResponse>> {
    return requestStudentCourses.retrieveCourses()
  }
}