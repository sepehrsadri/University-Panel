package com.sadri.universitypanel.domain.splash.core.ports.incoming

interface RequestLogout {
  suspend fun logout()
}