package com.sadri.universitypanel.domain.splash.core

import com.sadri.universitypanel.domain.splash.core.model.UserAuthenticationState
import com.sadri.universitypanel.domain.splash.core.model.UserInfo
import com.sadri.universitypanel.domain.splash.core.ports.incoming.GetUserAuthenticationState
import com.sadri.universitypanel.domain.splash.core.ports.incoming.RetrieveUserInfo
import com.sadri.universitypanel.domain.splash.core.ports.incoming.RetrieveUserToken
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.ReadUserInfoDatabase
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.ReadUserTokenDatabase
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthenticationDatabase
import kotlinx.coroutines.flow.Flow

class SplashFacade(
  private val userAuthenticationDatabase: UserAuthenticationDatabase,
  private val readUserInfoDatabase: ReadUserInfoDatabase,
  private val readUserTokenDatabase: ReadUserTokenDatabase
) : GetUserAuthenticationState, RetrieveUserInfo, RetrieveUserToken {
  override suspend fun handle(): Flow<UserAuthenticationState> {
    return userAuthenticationDatabase.retrieveState()
  }

  override suspend fun retrieveUserInfo(): Flow<UserInfo> {
    return readUserInfoDatabase.handle()
  }

  override suspend fun retrieveToken(): Flow<String> {
    return readUserTokenDatabase.retrieveToken()
  }
}