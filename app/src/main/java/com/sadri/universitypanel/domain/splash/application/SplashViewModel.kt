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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val getUserAuthenticationState: GetUserAuthenticationState
) : ViewModel() {

  private val _viewState: MutableLiveData<Event<SplashViewState>> = MutableLiveData()
  val viewState: LiveData<Event<SplashViewState>> get() = _viewState

  init {
    Timber.d("WTF : init ! ")
    viewModelScope.launch {
      getUserAuthenticationState.handle().collect { state ->
        when (state) {
          is UserAuthenticationState.NotAuthenticated -> {
            Timber.d(" WTF : called with NotAuthenticated ")
            _viewState.value = Event(SplashViewState(SplashUserState.NOT_AUTHENTICATED))
          }
          is UserAuthenticationState.Authenticated -> {
            Timber.d(" WTF : called with Authenticated ")
            _viewState.value = Event(SplashViewState(SplashUserState.AUTHENTICATED))
          }
        }
      }

    }
  }

}