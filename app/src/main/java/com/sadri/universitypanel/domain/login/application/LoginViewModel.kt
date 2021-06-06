package com.sadri.universitypanel.domain.login.application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.login.core.model.AuthenticateRequest
import com.sadri.universitypanel.domain.login.core.ports.incoming.OnUserAuthenticate
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SaveUserAuthenticationDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val onUserAuthenticate: OnUserAuthenticate
) : ViewModel() {

  val number: MutableLiveData<String> = MutableLiveData("12345")
  val password: MutableLiveData<String> = MutableLiveData("admin")

  fun submit() {
    viewModelScope.launch {
      val result = onUserAuthenticate.handle(
        AuthenticateRequest(number = number.value!!, password = password.value!!)
      )
      Timber.d("WTF : result : $result")
    }
  }

  fun onNumberChanged(number: String) {
    this.number.value = number
  }

  fun onPasswordChanged(password: String) {
    this.password.value = password
  }


}