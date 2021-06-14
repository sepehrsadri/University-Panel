package com.sadri.universitypanel.domain.instructor.section.application

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sadri.universitypanel.domain.instructor.home.core.model.SectionStudentResponse
import com.sadri.universitypanel.domain.login.core.model.ToastViewState
import com.sadri.universitypanel.domain.splash.application.Screens
import com.sadri.universitypanel.infrastructure.ui.ProfileTopAppBar
import com.sadri.universitypanel.infrastructure.ui.ProgressBar
import com.sadri.universitypanel.infrastructure.ui.SnackBar

@ExperimentalMaterialApi
@Composable
fun InstructorSectionScreen(
  modifier: Modifier,
  viewModel: InstructorSectionViewModel,
  navController: NavController
) {
  val viewState = viewModel.viewState.observeAsState().value!!

  viewModel.error.observeAsState().value?.getContentIfNotHandled()?.let {
    if (it is ToastViewState.Show) {
      SnackBar(modifier = modifier, text = it.text, dismiss = { viewModel.dismissToast() })
    }
  }

  val scrollState = rememberLazyListState()

  Scaffold(
    topBar = {
      ProfileTopAppBar(
        text = "Students",
        onBackClicked = {
          navController.navigate(Screens.InstructorHome.route)
        }
      )
    },
    content = {
      if (viewState.isLoading) {
        ProgressBar(modifier)
      }
      StudentsList(
        modifier = modifier,
        students = viewState.students,
        state = scrollState
      )
    }
  )
}


@Composable
fun StudentsList(
  students: List<SectionStudentResponse>,
  state: LazyListState,
  modifier: Modifier = Modifier
) {
  LazyColumn(modifier = modifier, state = state) {
    items(students.size) { index ->
      ListItem(
        student = students[index],
        modifier = modifier
      )
      Divider()
    }
  }
}

@Composable
fun ListItem(
  student: SectionStudentResponse,
  modifier: Modifier = Modifier
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .padding(16.dp)
  ) {
    Spacer(Modifier.width(10.dp))
    Text(
      text = student.name
    )
  }
}