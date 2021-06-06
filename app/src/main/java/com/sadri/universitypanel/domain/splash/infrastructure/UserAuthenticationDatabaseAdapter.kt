package com.sadri.universitypanel.domain.splash.infrastructure

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.sadri.universitypanel.domain.home.core.ports.outgoing.ReadUserInfoDatabase
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SaveUserAuthenticationDatabase
import com.sadri.universitypanel.domain.splash.core.model.UserAuthenticationState
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthenticationDatabase
import com.sadri.universitypanel.infrastructure.utils.PreferencesKeys
import com.sadri.universitypanel.infrastructure.utils.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.lang.RuntimeException


class UserAuthenticationDatabaseAdapter(
  private val context: Context
) : UserAuthenticationDatabase, SaveUserAuthenticationDatabase, ReadUserInfoDatabase {

  override suspend fun getState(): Flow<UserAuthenticationState> {
    return context.dataStore.data.map { preferences ->
      val existToken = preferences[PreferencesKeys.USER_TOKEN]
      if (existToken.isNullOrEmpty())
        UserAuthenticationState.NotAuthenticated else
        UserAuthenticationState.Authenticated(existToken)
    }
  }

  override suspend fun handle(authenticationResponse: AuthenticationResponse?) {
    authenticationResponse ?: return
    context.dataStore.edit { preferences ->
      preferences[PreferencesKeys.USER_TOKEN] = authenticationResponse.token
      preferences[PreferencesKeys.USER_NAME] = authenticationResponse.name
    }
  }

  override suspend fun handle(): Flow<AuthenticationResponse> {
    return context.dataStore.data.map { preferences ->
      val existToken = preferences[PreferencesKeys.USER_TOKEN]
        ?: ""
      val existName = preferences[PreferencesKeys.USER_NAME] ?: ""
      AuthenticationResponse(
        token = existToken,
        name = existName
      )
    }
  }

}