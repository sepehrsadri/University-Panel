package com.sadri.universitypanel.domain.instructor.grade.application

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MarkChatRead
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sadri.universitypanel.domain.instructor.home.core.model.SectionStudentResponse
import com.sadri.universitypanel.domain.splash.application.Screens
import com.sadri.universitypanel.infrastructure.ui.ProfileTopAppBar
import com.sadri.universitypanel.infrastructure.ui.ProgressBar
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun InstructorGradeScreen(
  modifier: Modifier,
  viewModel: InstructorGradeViewModel,
  navController: NavController
) {
  val viewState = viewModel.viewState.observeAsState().value!!
  val messageState = viewModel.message.observeAsState().value!!

  val scrollState = rememberLazyListState()
  val snackBarHostState = remember { SnackbarHostState() }
  val coroutineScope = rememberCoroutineScope()

  Scaffold(
    scaffoldState = rememberScaffoldState(snackbarHostState = snackBarHostState),
    floatingActionButton = {
      ExtendedFloatingActionButton(
        text = { Text(text = "Submit") },
        onClick = { viewModel.submit() },
        icon = {
          Icon(
            Icons.Filled.MarkChatRead,
            "",
            tint = Color.White
          )
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White
      )
    },
    topBar = {
      ProfileTopAppBar(
        text = "Students",
        onBackClicked = {
          navController.navigate(Screens.InstructorHome.route)
        }
      )
    },
    content = {
      if (messageState.isNotEmpty()) {
        coroutineScope.launch {
          snackBarHostState.showSnackbar(
            message = messageState,
            actionLabel = "Dismiss"
          )
          viewModel.dismissSnackBar()
        }
      }
      if (viewState.isLoading) {
        ProgressBar(modifier)
      }
      StudentsList(
        modifier = modifier,
        students = viewState.students,
        state = scrollState,
        onGradeChanged = { id, grade ->
          viewModel.onGradeChanged(id, grade)
        }
      )
    }
  )
}

@Composable
fun StudentsList(
  students: List<SectionStudentResponse>,
  state: LazyListState,
  modifier: Modifier = Modifier,
  onGradeChanged: (Int, String) -> Unit
) {
  LazyColumn(modifier = modifier, state = state) {
    items(students.size) { index ->
      ListItem(
        student = students[index],
        modifier = modifier,
        onGradeChanged = onGradeChanged
      )
      Divider()
    }
  }
}

@Composable
fun ListItem(
  student: SectionStudentResponse,
  modifier: Modifier = Modifier,
  onGradeChanged: (Int, String) -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .padding(16.dp)
  ) {
    Spacer(Modifier.width(10.dp))
    Text(
      text = student.name,
      modifier = modifier.padding(8.dp)
    )
    OutlinedTextField(
      value = student.grade,
      onValueChange = { onGradeChanged(student.id, it) },
      label = { Text(text = "Grade") },
      modifier = modifier
        .padding(8.dp)
        .requiredWidth(71.dp)
    )

  }
}