package com.sadri.universitypanel.domain.home.core.ports.outgoing

import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import kotlinx.coroutines.flow.Flow

interface ReadUserInfoDatabase {
  suspend fun handle(): Flow<AuthenticationResponse>
}