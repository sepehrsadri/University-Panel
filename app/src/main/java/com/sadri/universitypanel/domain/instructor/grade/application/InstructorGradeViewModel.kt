package com.sadri.universitypanel.domain.instructor.grade.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.instructor.grade.core.model.InstructorGradeViewState
import com.sadri.universitypanel.domain.instructor.grade.core.model.StudentGradeRequest
import com.sadri.universitypanel.domain.instructor.grade.core.ports.incoming.SubmitStudentsGrade
import com.sadri.universitypanel.domain.instructor.home.core.model.SectionStudentResponse
import com.sadri.universitypanel.domain.instructor.home.core.ports.incoming.RetrieveSectionStudents
import com.sadri.universitypanel.domain.splash.application.INSTRUCTOR_GRADE_SCREEN_ARGUMENT_ID_KEY
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructorGradeViewModel @Inject constructor(
  private val retrieveSectionStudents: RetrieveSectionStudents,
  private val submitStudentsGrade: SubmitStudentsGrade,
  savedStateHandle: SavedStateHandle
) : ViewModel() {
  private val _viewState: MutableLiveData<InstructorGradeViewState> =
    MutableLiveData(InstructorGradeViewState())
  val viewState: LiveData<InstructorGradeViewState> get() = _viewState

  private val _message: MutableLiveData<String> = MutableLiveData("")
  val message: LiveData<String> get() = _message

  private val sectionId: Int = savedStateHandle.get<Int>(INSTRUCTOR_GRADE_SCREEN_ARGUMENT_ID_KEY)
    ?: throw RuntimeException("no id passed for section screen")

  init {
    viewModelScope.launch {
      val sectionsResponse = retrieveSectionStudents.retrieveStudents(sectionId)
      if (
        sectionsResponse is ApiResult.Success &&
        sectionsResponse.data.isNullOrEmpty().not()
      ) {
        _viewState.value = InstructorGradeViewState(students = sectionsResponse.data!!)
      } else if (
        sectionsResponse is ApiResult.Error
      ) {
        _message.value = sectionsResponse.getFormattedText()
      }
    }
  }

  fun onGradeChanged(id: Int, grade: String) {
    val currentStudentList = _viewState.value!!.students
    val existItemIndex = currentStudentList.indexOfFirst { it.id == id }
    val existItem = currentStudentList[existItemIndex]
    val updatedItem = SectionStudentResponse(
      id = id,
      name = existItem.name,
      grade = grade
    )
    val updatedStudentsList = mutableListOf(*currentStudentList.toTypedArray())
    updatedStudentsList[existItemIndex] = updatedItem
    _viewState.value = _viewState.value!!.copy(
      students = updatedStudentsList
    )
  }

  fun submit() {
    val requestList = StudentGradeRequest.mapToStudentGradeRequest(
      sectionId = sectionId,
      list = _viewState.value!!.students
    )
    viewModelScope.launch {
      val messageText = when (val response = submitStudentsGrade.submit(requestList)) {
        is ApiResult.Success -> {
          "Submitted Successfully !"
        }
        is ApiResult.Error -> {
          response.getFormattedText()
        }
      }
      _message.value = messageText
    }
  }

  fun dismissSnackBar() {
    _message.value = ""
  }

}