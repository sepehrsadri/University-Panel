package com.sadri.universitypanel.domain.student.home.core

import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import com.sadri.universitypanel.domain.student.home.core.model.CourseResponse
import com.sadri.universitypanel.domain.student.home.core.ports.incoming.GetAuthenticatedStudentInfo
import com.sadri.universitypanel.domain.student.home.core.ports.incoming.GetStudentCourses
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.GetCoursesRequest
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.ReadAuthenticatedStudentInfoDatabase
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class StudentHomeFacade(
  private val readAuthenticatedStudentInfoDatabase: ReadAuthenticatedStudentInfoDatabase,
  private val getCoursesRequest: GetCoursesRequest
) : GetAuthenticatedStudentInfo, GetStudentCourses {
  override suspend fun handle(): Flow<AuthenticationResponse> {
    return readAuthenticatedStudentInfoDatabase.handle()
  }

  override suspend fun retrieveCourses(): ApiResult<List<CourseResponse>> {
    return getCoursesRequest.getCourses()
  }
}