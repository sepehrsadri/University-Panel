package com.sadri.universitypanel.domain.student.home.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.login.core.model.ToastViewState
import com.sadri.universitypanel.domain.student.home.core.model.StudentHomeViewState
import com.sadri.universitypanel.domain.student.home.core.ports.incoming.RetrieveStudentCourses
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import com.sadri.universitypanel.infrastructure.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
  private val retrieveStudentCourses: RetrieveStudentCourses
) : ViewModel() {
  private val _viewState: MutableLiveData<StudentHomeViewState> =
    MutableLiveData(StudentHomeViewState())
  val viewState: LiveData<StudentHomeViewState> get() = _viewState

  private val _error: MutableLiveData<Event<ToastViewState>> = MutableLiveData()
  val error: LiveData<Event<ToastViewState>> get() = _error

  init {
    viewModelScope.launch {
      val coursesResponse = retrieveStudentCourses.retrieveCourses()
      if (
        coursesResponse is ApiResult.Success &&
        coursesResponse.data.isNullOrEmpty().not()
      ) {
        _viewState.value = StudentHomeViewState(
          courses = coursesResponse.data!!,
          isLoading = false
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

}