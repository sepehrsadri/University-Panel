package com.sadri.universitypanel.infrastructure.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
  val USER_TOKEN = stringPreferencesKey("user_token")
  val USER_NAME = stringPreferencesKey("user_name")
  val USER_RULE = stringPreferencesKey("user_rule")
}