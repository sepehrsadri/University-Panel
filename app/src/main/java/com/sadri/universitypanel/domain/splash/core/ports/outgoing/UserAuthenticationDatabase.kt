package com.sadri.universitypanel.domain.splash.core.ports.outgoing

import com.sadri.universitypanel.domain.splash.core.model.UserAuthenticationState
import kotlinx.coroutines.flow.Flow

interface UserAuthenticationDatabase {
  suspend fun retrieveState(): Flow<UserAuthenticationState>
}