package com.sadri.universitypanel.infrastructure.utils

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.sadri.universitypanel.domain.login.core.model.UserRule
import retrofit2.Response
import timber.log.Timber

private const val USER_PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(
  name = USER_PREFERENCES_NAME
)

suspend fun <T> getResult(call: suspend () -> Response<T>): ApiResult<T> {
  return try {
    val response = call()
    val responseCode = response.code()
    return if (response.isSuccessful) {
      ApiResult.Success(
        response.body()
      )
    } else {
      Timber.e(RuntimeException("Server Error with code $responseCode"))
      ApiResult.Error(
        text = "Server Error !",
        code = responseCode
      )
    }
  } catch (e: Exception) {
    Timber.e(e)
    return ApiResult.Error(text = "Network Error !")
  }
}

fun String?.getUserRule(): UserRule {
  return UserRule.getRule(this)
}

fun ApiResult<*>.isError(): Boolean {
  return this is ApiResult.Error
}

fun Boolean?.isFalseOrNull(): Boolean {
  return when {
    this == null -> true
    else -> !this
  }
}