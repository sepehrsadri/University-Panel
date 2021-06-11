package com.sadri.universitypanel.domain.splash.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.splash.core.model.SplashUserState
import com.sadri.universitypanel.domain.splash.core.model.SplashViewState
import com.sadri.universitypanel.domain.splash.core.model.UserAuthenticationState
import com.sadri.universitypanel.domain.splash.core.ports.incoming.GetUserAuthenticationState
import com.sadri.universitypanel.infrastructure.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val getUserAuthenticationState: GetUserAuthenticationState
) : ViewModel() {

  private val _viewState: MutableLiveData<Event<SplashViewState>> = MutableLiveData()
  val viewState: LiveData<Event<SplashViewState>> get() = _viewState

  init {
    viewModelScope.launch {
      delay(500L)
      getUserAuthenticationState.handle().collect { state ->
        when (state) {
          UserAuthenticationState.NOT_AUTHENTICATED -> {
            _viewState.value = Event(SplashViewState(SplashUserState.NOT_AUTHENTICATED))
          }
          UserAuthenticationState.AUTHENTICATED_STUDENT -> {
            _viewState.value = Event(SplashViewState(SplashUserState.AUTHENTICATED_STUDENT))
          }
          UserAuthenticationState.AUTHENTICATED_MASTER -> {
            _viewState.value = Event(SplashViewState(SplashUserState.AUTHENTICATED_MASTER))
          }
        }
      }

    }
  }

}