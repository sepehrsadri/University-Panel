package com.sadri.universitypanel.domain.student.home.core

import com.sadri.universitypanel.domain.student.home.core.ports.incoming.GetAuthenticatedStudentInfo
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.ReadAuthenticatedStudentInfoDatabase
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import kotlinx.coroutines.flow.Flow

class StudentHomeFacade(
  private val readAuthenticatedStudentInfoDatabase: ReadAuthenticatedStudentInfoDatabase
) : GetAuthenticatedStudentInfo {
  override suspend fun handle(): Flow<AuthenticationResponse> {
    return readAuthenticatedStudentInfoDatabase.handle()
  }
}