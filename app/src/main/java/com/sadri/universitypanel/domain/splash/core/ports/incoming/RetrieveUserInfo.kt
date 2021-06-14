package com.sadri.universitypanel.domain.splash.core.ports.incoming

import com.sadri.universitypanel.domain.splash.core.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface RetrieveUserInfo {
  suspend fun retrieveUserInfo(): Flow<UserInfo>
}