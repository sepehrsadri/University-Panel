package com.sadri.universitypanel.domain.splash.core.ports.incoming

interface AuthStateChanged {
  fun handle(token: String?)
}