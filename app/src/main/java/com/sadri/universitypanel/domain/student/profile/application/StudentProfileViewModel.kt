package com.sadri.universitypanel.domain.student.profile.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.splash.core.ports.incoming.RequestLogout
import com.sadri.universitypanel.domain.splash.core.ports.incoming.RetrieveUserInfo
import com.sadri.universitypanel.domain.student.profile.core.model.StudentProfileViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentProfileViewModel @Inject constructor(
  private val retrieveUserInfo: RetrieveUserInfo,
  private val requestLogout: RequestLogout
) : ViewModel() {
  private val _viewState: MutableLiveData<StudentProfileViewState> =
    MutableLiveData(StudentProfileViewState())
  val viewState: LiveData<StudentProfileViewState> get() = _viewState

  init {
    viewModelScope.launch {
      retrieveUserInfo.retrieveUserInfo().collect { userInfo ->
        _viewState.value =
          StudentProfileViewState(
            name = userInfo.name,
            number = userInfo.number
          )
      }
    }
  }

  fun logout() {
    viewModelScope.launch {
      requestLogout.logout()
    }
  }

}