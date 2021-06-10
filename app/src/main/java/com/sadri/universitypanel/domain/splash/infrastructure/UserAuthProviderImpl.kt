package com.sadri.universitypanel.domain.splash.infrastructure

import com.sadri.universitypanel.domain.splash.core.ports.incoming.AuthStateChanged
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthProvider
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.ReadAuthenticatedStudentInfoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserAuthProviderImpl(
  private val readAuthenticatedStudentInfoDatabase: ReadAuthenticatedStudentInfoDatabase
) : UserAuthProvider, AuthStateChanged {
  private var token: String? = null
  override val isLoggedIn: Boolean get() = token.isNullOrEmpty().not()

  init {
    GlobalScope.launch(Dispatchers.IO) {
      readAuthenticatedStudentInfoDatabase.handle().collect {
        token = it.token
      }
    }
  }

  override fun handle(token: String?) {
    this.token = token
  }

  override fun retrieveToken() = token!!
}