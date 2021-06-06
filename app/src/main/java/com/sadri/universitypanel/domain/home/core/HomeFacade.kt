package com.sadri.universitypanel.domain.home.core

import com.sadri.universitypanel.domain.home.core.ports.incoming.GetAuthenticatedUserInfo
import com.sadri.universitypanel.domain.home.core.ports.outgoing.ReadUserInfoDatabase
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import kotlinx.coroutines.flow.Flow

class HomeFacade(
  private val readUserInfoDatabase: ReadUserInfoDatabase
) : GetAuthenticatedUserInfo {
  override suspend fun handle(): Flow<AuthenticationResponse> {
    return readUserInfoDatabase.handle()
  }
}