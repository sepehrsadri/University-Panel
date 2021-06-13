package com.sadri.universitypanel.domain.student.home.core

import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import com.sadri.universitypanel.domain.student.home.core.model.StudentCourseResponse
import com.sadri.universitypanel.domain.student.home.core.ports.incoming.RetrieveAuthenticatedStudentInfo
import com.sadri.universitypanel.domain.student.home.core.ports.incoming.RetrieveStudentCourses
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.ReadAuthenticatedStudentInfoDatabase
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.RequestStudentCourses
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class StudentFacade(
  private val readAuthenticatedStudentInfoDatabase: ReadAuthenticatedStudentInfoDatabase,
  private val requestStudentCourses: RequestStudentCourses
) : RetrieveAuthenticatedStudentInfo, RetrieveStudentCourses {
  override suspend fun handle(): Flow<AuthenticationResponse> {
    return readAuthenticatedStudentInfoDatabase.handle()
  }

  override suspend fun retrieveCourses(): ApiResult<List<StudentCourseResponse>> {
    return requestStudentCourses.retrieveCourses()
  }
}