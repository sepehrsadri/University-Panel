package com.sadri.universitypanel.domain.master.home.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.ReadAuthenticatedStudentInfoDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasterHomeViewModel @Inject constructor(
  private val readAuthenticatedStudentInfoDatabase: ReadAuthenticatedStudentInfoDatabase
) : ViewModel() {
  private val _viewState: MutableLiveData<String> = MutableLiveData()
  val viewState: LiveData<String> get() = _viewState

  init {
    viewModelScope.launch {
      readAuthenticatedStudentInfoDatabase.handle().collect { authenticationResponse ->
        _viewState.value = authenticationResponse.name
      }
    }
  }

}