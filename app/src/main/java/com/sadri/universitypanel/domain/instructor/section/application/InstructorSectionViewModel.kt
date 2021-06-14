package com.sadri.universitypanel.domain.instructor.section.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.instructor.home.core.ports.incoming.RetrieveSectionStudents
import com.sadri.universitypanel.domain.instructor.section.core.model.InstructorSectionViewState
import com.sadri.universitypanel.domain.login.core.model.ToastViewState
import com.sadri.universitypanel.domain.splash.application.INSTRUCTOR_SECTION_SCREEN_ARGUMENT_ID_KEY
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import com.sadri.universitypanel.infrastructure.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructorSectionViewModel @Inject constructor(
  private val retrieveSectionStudents: RetrieveSectionStudents,
  savedStateHandle: SavedStateHandle
) : ViewModel() {
  private val _viewState: MutableLiveData<InstructorSectionViewState> =
    MutableLiveData(InstructorSectionViewState())
  val viewState: LiveData<InstructorSectionViewState> get() = _viewState

  private val _error: MutableLiveData<Event<ToastViewState>> = MutableLiveData()
  val error: LiveData<Event<ToastViewState>> get() = _error

  init {
    viewModelScope.launch {
      val sectionsResponse = retrieveSectionStudents.retrieveStudents(
        savedStateHandle.get<Int>(INSTRUCTOR_SECTION_SCREEN_ARGUMENT_ID_KEY)
          ?: throw RuntimeException("no id passed for section screen")
      )
      if (
        sectionsResponse is ApiResult.Success &&
        sectionsResponse.data.isNullOrEmpty().not()
      ) {
        _viewState.value = InstructorSectionViewState(
          students = sectionsResponse.data!!,
          isLoading = false
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

}