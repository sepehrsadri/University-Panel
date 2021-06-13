package com.sadri.universitypanel.domain.splash.core

import com.sadri.universitypanel.domain.splash.core.model.UserAuthenticationState
import com.sadri.universitypanel.domain.splash.core.ports.incoming.GetUserAuthenticationState
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthenticationDatabase
import kotlinx.coroutines.flow.Flow

class SplashFacade(
  private val userAuthenticationDatabase: UserAuthenticationDatabase
) : GetUserAuthenticationState {
  override suspend fun handle(): Flow<UserAuthenticationState> {
    return userAuthenticationDatabase.retrieveState()
  }
}