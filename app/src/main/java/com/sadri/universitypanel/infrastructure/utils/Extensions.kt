package com.sadri.universitypanel.infrastructure.utils

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

private const val USER_PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(
  name = USER_PREFERENCES_NAME
)

suspend fun <T> getResult(call: suspend () -> Response<T>): ApiResult<T> {
  return try {
    val response = call()
    Timber.d("WTF : response ${response.code()}")
    if (response.isSuccessful) {
      ApiResult.Success(
        response.body()
      )
    } else {
      ApiResult.Error(RuntimeException("notSuccessfull"))
    }
  } catch (e: Exception) {
    Timber.e(e)
    ApiResult.Error(e)
  }
}