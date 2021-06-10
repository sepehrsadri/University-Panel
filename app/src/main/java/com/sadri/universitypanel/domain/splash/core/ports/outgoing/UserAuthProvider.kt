package com.sadri.universitypanel.domain.splash.core.ports.outgoing

interface UserAuthProvider {
  val isLoggedIn: Boolean
  fun retrieveToken(): String
}