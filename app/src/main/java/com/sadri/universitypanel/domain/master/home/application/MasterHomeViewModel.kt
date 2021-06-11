package com.sadri.universitypanel.domain.master.home.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.login.core.model.ToastViewState
import com.sadri.universitypanel.domain.master.home.core.model.MasterHomeViewState
import com.sadri.universitypanel.domain.splash.core.ports.incoming.RequestLogout
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.ReadAuthenticatedStudentInfoDatabase
import com.sadri.universitypanel.infrastructure.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasterHomeViewModel @Inject constructor(
  private val readAuthenticatedStudentInfoDatabase: ReadAuthenticatedStudentInfoDatabase,
  private val requestLogout: RequestLogout
) : ViewModel() {
  private val _viewState: MutableLiveData<MasterHomeViewState> =
    MutableLiveData(MasterHomeViewState())
  val viewState: LiveData<MasterHomeViewState> get() = _viewState

  private val _error: MutableLiveData<Event<ToastViewState>> = MutableLiveData()
  val error: LiveData<Event<ToastViewState>> get() = _error

  init {
    viewModelScope.launch {
      readAuthenticatedStudentInfoDatabase.handle().collect { authenticationResponse ->
        _viewState.value = _viewState.value!!.copy(
          name = authenticationResponse.name,
          isLoading = false
        )
      }
    }
  }

  fun dismissToast() {
    _error.value = Event(ToastViewState.Hide)
  }

  fun logout() {
    viewModelScope.launch {
      requestLogout.logout()
    }
  }

}