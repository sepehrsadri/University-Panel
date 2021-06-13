package com.sadri.universitypanel.domain.student.home.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.login.core.model.ToastViewState
import com.sadri.universitypanel.domain.splash.core.ports.incoming.RequestLogout
import com.sadri.universitypanel.domain.student.home.core.model.StudentHomeViewState
import com.sadri.universitypanel.domain.student.home.core.ports.incoming.RetrieveStudentCourses
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.ReadAuthenticatedStudentInfoDatabase
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import com.sadri.universitypanel.infrastructure.utils.Event
import com.sadri.universitypanel.infrastructure.utils.MultiSourceBooleanMediatorLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
  private val readAuthenticatedStudentInfoDatabase: ReadAuthenticatedStudentInfoDatabase,
  private val retrieveStudentCourses: RetrieveStudentCourses,
  private val requestLogout: RequestLogout
) : ViewModel() {
  private val _viewState: MutableLiveData<StudentHomeViewState> =
    MutableLiveData(StudentHomeViewState())
  val viewState: LiveData<StudentHomeViewState> get() = _viewState

  private val _error: MutableLiveData<Event<ToastViewState>> = MutableLiveData()
  val error: LiveData<Event<ToastViewState>> get() = _error

  private val _loading = MultiSourceBooleanMediatorLiveData()
  val loading: LiveData<Boolean> get() = _loading

  init {
    _loading.addBooleanSource(
      viewState
    ) {
      return@addBooleanSource it!!.name.isNotEmpty() && it.courses.isNotEmpty()
    }

    viewModelScope.launch {
      readAuthenticatedStudentInfoDatabase.handle().collect { authenticationResponse ->
        _viewState.value = _viewState.value!!.copy(
          name = authenticationResponse.name
        )
      }
    }
    viewModelScope.launch {
      val coursesResponse = retrieveStudentCourses.retrieveCourses()
      if (
        coursesResponse is ApiResult.Success &&
        coursesResponse.data.isNullOrEmpty().not()
      ) {
        _viewState.value = _viewState.value!!.copy(
          courses = coursesResponse.data!!
        )
      } else if (
        coursesResponse is ApiResult.Error
      ) {
        _error.value = Event(ToastViewState.Show(coursesResponse.getFormattedText()))
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