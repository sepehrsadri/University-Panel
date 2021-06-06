package com.sadri.universitypanel.domain.splash.core.ports.incoming

import com.sadri.universitypanel.domain.splash.core.model.UserAuthenticationState
import kotlinx.coroutines.flow.Flow

interface GetUserAuthenticationState {
  suspend fun handle(): Flow<UserAuthenticationState>
}