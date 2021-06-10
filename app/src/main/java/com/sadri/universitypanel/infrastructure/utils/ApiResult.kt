package com.sadri.universitypanel.infrastructure.utils


sealed class ApiResult<out T> {
  data class Success<T>(val data: T?) : ApiResult<T>()
  data class Error(
    val text: String,
    val code: Int = -1
  ) : ApiResult<Nothing>() {
    fun getFormattedText(): String {
      return if (code != -1) {
        " $text - error code : $code"
      } else text
    }
  }
}

