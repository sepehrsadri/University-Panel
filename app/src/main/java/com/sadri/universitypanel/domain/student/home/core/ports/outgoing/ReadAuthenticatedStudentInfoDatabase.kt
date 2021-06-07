package com.sadri.universitypanel.domain.student.home.core.ports.outgoing

import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import kotlinx.coroutines.flow.Flow

interface ReadAuthenticatedStudentInfoDatabase {
  suspend fun handle(): Flow<AuthenticationResponse>
}