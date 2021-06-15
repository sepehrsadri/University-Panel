package com.sadri.universitypanel.domain.instructor.home.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.instructor.home.core.model.InstructorHomeViewState
import com.sadri.universitypanel.domain.instructor.home.core.ports.incoming.RetrieveInstructorSections
import com.sadri.universitypanel.domain.splash.core.ports.incoming.RequestLogout
import com.sadri.universitypanel.domain.splash.core.ports.incoming.RetrieveUserInfo
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructorHomeViewModel @Inject constructor(
  private val retrieveUserInfo: RetrieveUserInfo,
  private val retrieveInstructorSections: RetrieveInstructorSections,
  private val requestLogout: RequestLogout
) : ViewModel() {
  private val _viewState: MutableLiveData<InstructorHomeViewState> =
    MutableLiveData(InstructorHomeViewState())
  val viewState: LiveData<InstructorHomeViewState> get() = _viewState

  private val _message: MutableLiveData<String> = MutableLiveData("")
  val message: LiveData<String> get() = _message

  init {
    viewModelScope.launch {
      retrieveUserInfo.retrieveUserInfo().collect { userInfo ->
        _viewState.value = _viewState.value!!.copy(
          name = userInfo.name
        )
      }
    }
    viewModelScope.launch {
      val sectionsResponse = retrieveInstructorSections.retrieveSections()
      if (
        sectionsResponse is ApiResult.Success &&
        sectionsResponse.data.isNullOrEmpty().not()
      ) {
        _viewState.value = _viewState.value!!.copy(
          sections = sectionsResponse.data!!
        )
      } else if (
        sectionsResponse is ApiResult.Error
      ) {
        _message.value = sectionsResponse.getFormattedText()
      }
    }
  }

  fun dismissSnackBar() {
    _message.value = ""
  }

  fun logout() {
    viewModelScope.launch {
      requestLogout.logout()
    }
  }

}