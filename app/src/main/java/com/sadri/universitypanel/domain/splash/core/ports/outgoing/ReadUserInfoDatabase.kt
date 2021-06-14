package com.sadri.universitypanel.domain.splash.core.ports.outgoing

import com.sadri.universitypanel.domain.splash.core.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface ReadUserInfoDatabase {
  suspend fun handle(): Flow<UserInfo>
}