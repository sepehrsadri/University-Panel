package com.sadri.universitypanel.domain.login.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.login.core.model.AuthenticateRequest
import com.sadri.universitypanel.domain.login.core.model.ToastViewState
import com.sadri.universitypanel.domain.login.core.model.UserRule
import com.sadri.universitypanel.domain.login.core.ports.incoming.OnUserAuthenticate
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import com.sadri.universitypanel.infrastructure.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val onUserAuthenticate: OnUserAuthenticate
) : ViewModel() {

  val number: MutableLiveData<String> = MutableLiveData("10101")
  val password: MutableLiveData<String> = MutableLiveData("admin")
  val rule: MutableLiveData<UserRule> = MutableLiveData(UserRule.INSTRUCTOR)

  private val _error: MutableLiveData<Event<ToastViewState>> = MutableLiveData()
  val error: LiveData<Event<ToastViewState>> get() = _error

  fun submit() {
    viewModelScope.launch {
      val result = onUserAuthenticate.handle(
        userRule = rule.value!!,
        authenticateRequest = AuthenticateRequest(
          number = number.value!!,
          password = password.value!!,
        )
      )
      if (result is ApiResult.Error) {
        _error.value = Event(ToastViewState.Show(result.getFormattedText()))
      }
    }
  }

  fun dismissToast() {
    _error.value = Event(ToastViewState.Hide)
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