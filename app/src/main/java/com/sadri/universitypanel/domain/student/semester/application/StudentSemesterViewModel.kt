package com.sadri.universitypanel.domain.student.semester.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.student.semester.core.model.StudentSemesterResponse
import com.sadri.universitypanel.domain.student.semester.core.model.StudentSemesterViewState
import com.sadri.universitypanel.domain.student.semester.core.model.StudentTakesRequest
import com.sadri.universitypanel.domain.student.semester.core.ports.incoming.RetrieveStudentSemester
import com.sadri.universitypanel.domain.student.semester.core.ports.incoming.SubmitStudentTakes
import com.sadri.universitypanel.infrastructure.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StudentSemesterViewModel @Inject constructor(
  private val retrieveStudentSemester: RetrieveStudentSemester,
  private val submitStudentTakes: SubmitStudentTakes
) : ViewModel() {
  private val _viewState: MutableLiveData<StudentSemesterViewState> =
    MutableLiveData(StudentSemesterViewState())
  val viewState: LiveData<StudentSemesterViewState> get() = _viewState

  private val _message: MutableLiveData<String> = MutableLiveData("")
  val message: LiveData<String> get() = _message

  init {
    viewModelScope.launch {
      val takesResponse = retrieveStudentSemester.retrieveSemester()
      if (
        takesResponse is ApiResult.Success &&
        takesResponse.data.isNullOrEmpty().not()
      ) {
        _viewState.value = StudentSemesterViewState(takes = takesResponse.data!!)
      } else if (
        takesResponse is ApiResult.Error
      ) {
        _message.value = takesResponse.getFormattedText()
      }
    }
  }

  fun dismissSnackBar() {
    _message.value = ""
  }

  fun submit() {
    val requestList = StudentTakesRequest.mapToStudentTakesRequest(
      list = _viewState.value!!.takes
    )
    Timber.d("WTF : submit : $requestList")
    viewModelScope.launch {
      val messageText = when (val response = submitStudentTakes.submit(requestList)) {
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

  fun onTakeClick(id: Int) {
    val currentTakesList = _viewState.value!!.takes
    val existItemIndex = currentTakesList.indexOfFirst { it.id == id }
    val existItem = currentTakesList[existItemIndex]
    val updatedItem = StudentSemesterResponse(
      id = id,
      title = existItem.title,
      semester = existItem.semester,
      credits = existItem.credits,
      instructors = existItem.instructors,
      taken = existItem.taken.not()
    )
    val updatedTakesList = mutableListOf(*currentTakesList.toTypedArray())
    updatedTakesList[existItemIndex] = updatedItem
    _viewState.value = _viewState.value!!.copy(
      takes = updatedTakesList
    )
  }


}