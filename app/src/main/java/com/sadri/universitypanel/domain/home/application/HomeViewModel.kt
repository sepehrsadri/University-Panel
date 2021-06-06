package com.sadri.universitypanel.domain.home.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.home.core.ports.outgoing.ReadUserInfoDatabase
import com.sadri.universitypanel.domain.login.core.model.AuthenticationResponse
import com.sadri.universitypanel.domain.splash.core.model.SplashViewState
import com.sadri.universitypanel.infrastructure.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val readUserInfoDatabase: ReadUserInfoDatabase
) : ViewModel() {
  private val _viewState: MutableLiveData<String> = MutableLiveData()
  val viewState: LiveData<String> get() = _viewState

  init {
    viewModelScope.launch {
      readUserInfoDatabase.handle().collect { authenticationResponse ->
        _viewState.value = authenticationResponse.name
      }
    }
  }

}