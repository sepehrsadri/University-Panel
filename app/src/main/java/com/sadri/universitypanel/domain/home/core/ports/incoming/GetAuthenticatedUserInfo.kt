package com.sadri.universitypanel.domain.home.core.ports.incoming

import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import kotlinx.coroutines.flow.Flow

interface GetAuthenticatedUserInfo {
  suspend fun handle(): Flow<AuthenticationResponse>
}