package com.sadri.universitypanel.domain.student.home.core.ports.incoming

import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import kotlinx.coroutines.flow.Flow

interface RetrieveAuthenticatedStudentInfo {
  suspend fun handle(): Flow<AuthenticationResponse>
}