package com.sadri.universitypanel.domain.student.semester.application

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import com.sadri.universitypanel.domain.student.semester.core.model.StudentSemesterResponse
import com.sadri.universitypanel.infrastructure.ui.ProgressBar
import kotlinx.coroutines.launch

@Composable
fun StudentSemesterScreen(
  modifier: Modifier,
  viewModel: StudentSemesterViewModel
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
        modifier = modifier.padding(bottom = 52.dp),
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
      TakesList(
        modifier = modifier,
        takes = viewState.takes,
        state = scrollState,
        onTakeClick = { viewModel.onTakeClick(it) }
      )
    }
  )
}


@Composable
fun TakesList(
  takes: List<StudentSemesterResponse>,
  state: LazyListState,
  modifier: Modifier = Modifier,
  onTakeClick: (Int) -> Unit
) {
  LazyColumn(modifier = modifier, state = state) {
    items(takes.size) { index ->
      ListItem(
        take = takes[index],
        modifier = modifier,
        onTakeClick = onTakeClick
      )
      Divider()
    }
  }
}

@Composable
fun ListItem(
  take: StudentSemesterResponse,
  modifier: Modifier = Modifier,
  onTakeClick: (Int) -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .padding(16.dp)
      .fillMaxWidth()
      .clickable { onTakeClick(take.id) }
  ) {
    Spacer(modifier.width(10.dp))
    Column {
      Row {
        Text(
          text = "Title : ${take.title}",
          style = MaterialTheme.typography.h6
        )
        Text(
          text = if (take.taken) "✓" else "",
          color = MaterialTheme.colors.primary,
          style = MaterialTheme.typography.h6,
          modifier = modifier.padding(start = 16.dp)
        )
      }
      Text(
        text = "Semester : ${take.semester}",
        style = MaterialTheme.typography.subtitle2
      )
      Text(
        text = "Instructor : ${take.instructors}",
        style = MaterialTheme.typography.subtitle2
      )
      Text(
        text = "Credits : ${take.credits}",
        style = MaterialTheme.typography.subtitle2
      )
    }
  }
}