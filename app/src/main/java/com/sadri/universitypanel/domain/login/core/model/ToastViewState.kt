package com.sadri.universitypanel.domain.login.core.model

sealed class ToastViewState {
  object Hide : ToastViewState()
  data class Show(val text: String) : ToastViewState()
}