package com.sadri.universitypanel.domain.splash.core.ports.outgoing

interface ClearUserInfoDatabase {
  suspend fun clear()
}