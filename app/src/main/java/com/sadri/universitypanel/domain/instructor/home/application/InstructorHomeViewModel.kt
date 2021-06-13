package com.sadri.universitypanel.domain.instructor.home.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.instructor.home.core.model.InstructorHomeViewState
import com.sadri.universitypanel.domain.instructor.home.core.ports.incoming.RetrieveInstructorSections
import com.sadri.universitypanel.domain.login.core.model.ToastViewState
import com.sadri.universitypanel.domain.splash.core.ports.incoming.RequestLogout
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.ReadAuthenticatedStudentInfoDatabase
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import com.sadri.universitypanel.infrastructure.utils.Event
import com.sadri.universitypanel.infrastructure.utils.MultiSourceBooleanMediatorLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructorHomeViewModel @Inject constructor(
  private val readAuthenticatedStudentInfoDatabase: ReadAuthenticatedStudentInfoDatabase,
  private val retrieveInstructorSections: RetrieveInstructorSections,
  private val requestLogout: RequestLogout
) : ViewModel() {
  private val _viewState: MutableLiveData<InstructorHomeViewState> =
    MutableLiveData(InstructorHomeViewState())
  val viewState: LiveData<InstructorHomeViewState> get() = _viewState

  private val _error: MutableLiveData<Event<ToastViewState>> = MutableLiveData()
  val error: LiveData<Event<ToastViewState>> get() = _error

  private val _loading = MultiSourceBooleanMediatorLiveData()
  val loading: LiveData<Boolean> get() = _loading

  init {
    _loading.addBooleanSource(
      viewState
    ) {
      return@addBooleanSource it!!.name.isNotEmpty() && it.sections.isNotEmpty()
    }

    viewModelScope.launch {
      readAuthenticatedStudentInfoDatabase.handle().collect { authenticationResponse ->
        _viewState.value = _viewState.value!!.copy(
          name = authenticationResponse.name
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
        _error.value = Event(ToastViewState.Show(sectionsResponse.getFormattedText()))
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

  override fun onCleared() {
    _loading.removeSource(viewState)
    super.onCleared()
  }

}