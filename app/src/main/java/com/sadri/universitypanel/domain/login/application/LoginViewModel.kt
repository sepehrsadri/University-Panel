package com.sadri.universitypanel.domain.login.application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.login.core.model.AuthenticateRequest
import com.sadri.universitypanel.domain.login.core.model.UserRule
import com.sadri.universitypanel.domain.login.core.ports.incoming.OnUserAuthenticate
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
  val rule: MutableLiveData<UserRule> = MutableLiveData(UserRule.STUDENT)

  fun submit() {
    viewModelScope.launch {
      val result = onUserAuthenticate.handle(
        userRule = rule.value!!,
        authenticateRequest = AuthenticateRequest(
          number = number.value!!,
          password = password.value!!,
        )
      )
      Timber.d("WTF : result : $result")
    }
  }

  fun onNumberChanged(number: String) {
    this.number.value = number
  }

  fun onRuleChanged(rule: UserRule) {
    this.rule.value = rule
  }

  fun onPasswordChanged(password: String) {
    this.password.value = password
  }

}