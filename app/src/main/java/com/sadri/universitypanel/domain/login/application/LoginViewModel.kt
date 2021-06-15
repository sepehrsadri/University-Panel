package com.sadri.universitypanel.domain.login.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.login.core.model.AuthenticateRequest
import com.sadri.universitypanel.domain.login.core.model.LoginViewState
import com.sadri.universitypanel.domain.login.core.model.UserRule
import com.sadri.universitypanel.domain.login.core.ports.incoming.OnUserAuthenticate
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val onUserAuthenticate: OnUserAuthenticate
) : ViewModel() {

  private val _viewState: MutableLiveData<LoginViewState> =
    MutableLiveData(LoginViewState())
  val viewState: LiveData<LoginViewState> get() = _viewState

  private val _message: MutableLiveData<String> = MutableLiveData("")
  val message: LiveData<String> get() = _message

  fun submit() {
    viewModelScope.launch {
      val result = onUserAuthenticate.handle(
        userRule = viewState.value!!.rule,
        authenticateRequest = AuthenticateRequest(
          number = viewState.value!!.number,
          password = viewState.value!!.password,
        )
      )
      if (result is ApiResult.Error) {
        _message.value = result.getFormattedText()
      }
    }
  }

  fun dismissSnackBar() {
    _message.value = ""
  }

  fun onNumberChanged(number: String) {
    _viewState.value = _viewState.value!!.copy(
      number = number
    )
  }

  fun onRuleChanged(rule: UserRule) {
    _viewState.value = _viewState.value!!.copy(
      rule = rule
    )
  }

  fun onPasswordChanged(password: String) {
    _viewState.value = _viewState.value!!.copy(
      password = password
    )
  }

}