package com.sadri.universitypanel.infrastructure.utils


sealed class ApiResult<out T> {
  data class Success<T>(val data: T?) : ApiResult<T>()
  data class Error(
    val exception: Exception
  ) : ApiResult<Nothing>()
}