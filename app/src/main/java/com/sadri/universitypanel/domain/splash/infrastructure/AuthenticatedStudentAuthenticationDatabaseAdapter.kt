package com.sadri.universitypanel.domain.splash.infrastructure

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import com.sadri.universitypanel.domain.login.core.model.UserRule
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SaveUserAuthenticationDatabase
import com.sadri.universitypanel.domain.splash.core.model.UserAuthenticationState
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.ClearUserInfoDatabase
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthenticationDatabase
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.ReadAuthenticatedStudentInfoDatabase
import com.sadri.universitypanel.infrastructure.utils.PreferencesKeys
import com.sadri.universitypanel.infrastructure.utils.dataStore
import com.sadri.universitypanel.infrastructure.utils.getUserRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class AuthenticatedStudentAuthenticationDatabaseAdapter(
  private val context: Context
) : UserAuthenticationDatabase, SaveUserAuthenticationDatabase,
  ReadAuthenticatedStudentInfoDatabase, ClearUserInfoDatabase {

  override suspend fun getState(): Flow<UserAuthenticationState> {
    return context.dataStore.data.map { preferences ->
      val existToken = preferences[PreferencesKeys.USER_TOKEN]
      when {
        existToken.isNullOrEmpty() -> UserAuthenticationState.NOT_AUTHENTICATED
        else -> {
          val userRule = preferences[PreferencesKeys.USER_RULE].getUserRule()
          if (userRule == UserRule.STUDENT)
            UserAuthenticationState.AUTHENTICATED_STUDENT else
            UserAuthenticationState.AUTHENTICATED_MASTER
        }
      }
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

  override suspend fun handle(userRule: UserRule, authenticationResponse: AuthenticationResponse?) {
    authenticationResponse ?: return
    context.dataStore.edit { preferences ->
      preferences[PreferencesKeys.USER_TOKEN] = authenticationResponse.token
      preferences[PreferencesKeys.USER_NAME] = authenticationResponse.name
      preferences[PreferencesKeys.USER_RULE] = userRule.getKey()
    }
  }

  override suspend fun clear() {
    context.dataStore.edit { preferences ->
      preferences.clear()
    }
  }

}