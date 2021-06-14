package com.sadri.universitypanel.domain.splash.infrastructure

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import com.sadri.universitypanel.domain.login.core.model.UserRule
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SaveUserAuthenticationDatabase
import com.sadri.universitypanel.domain.splash.core.model.UserAuthenticationState
import com.sadri.universitypanel.domain.splash.core.model.UserInfo
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.ClearUserInfoDatabase
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.ReadUserInfoDatabase
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.ReadUserTokenDatabase
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthenticationDatabase
import com.sadri.universitypanel.infrastructure.utils.PreferencesKeys
import com.sadri.universitypanel.infrastructure.utils.dataStore
import com.sadri.universitypanel.infrastructure.utils.getUserRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserDatabaseAdapter(
  private val context: Context
) : UserAuthenticationDatabase, SaveUserAuthenticationDatabase,
  ReadUserInfoDatabase, ClearUserInfoDatabase, ReadUserTokenDatabase {

  override suspend fun retrieveState(): Flow<UserAuthenticationState> {
    return context.dataStore.data.map { preferences ->
      val existToken = preferences[PreferencesKeys.USER_TOKEN]
      when {
        existToken.isNullOrEmpty() -> UserAuthenticationState.NOT_AUTHENTICATED
        else -> {
          val userRule = preferences[PreferencesKeys.USER_RULE].getUserRule()
          if (userRule == UserRule.STUDENT)
            UserAuthenticationState.AUTHENTICATED_STUDENT else
            UserAuthenticationState.AUTHENTICATED_INSTRUCTOR
        }
      }
    }
  }

  override suspend fun retrieveToken(): Flow<String> {
    return context.dataStore.data.map { preferences ->
      preferences[PreferencesKeys.USER_TOKEN] ?: ""
    }
  }

  override suspend fun handle(
    userRule: UserRule,
    authenticationResponse: AuthenticationResponse?,
    number: String,
  ) {
    authenticationResponse ?: return
    context.dataStore.edit { preferences ->
      preferences[PreferencesKeys.USER_TOKEN] = authenticationResponse.token
      preferences[PreferencesKeys.USER_NAME] = authenticationResponse.name
      preferences[PreferencesKeys.USER_RULE] = userRule.getKey()
      preferences[PreferencesKeys.USER_NUMBER] = number
    }
  }

  override suspend fun clear() {
    context.dataStore.edit { preferences ->
      preferences.clear()
    }
  }

  override suspend fun handle(): Flow<UserInfo> {
    return context.dataStore.data.map { preferences ->
      val existName = preferences[PreferencesKeys.USER_NAME] ?: ""
      val existNumber = preferences[PreferencesKeys.USER_NUMBER] ?: ""
      UserInfo(
        name = existName,
        number = existNumber
      )
    }
  }

}