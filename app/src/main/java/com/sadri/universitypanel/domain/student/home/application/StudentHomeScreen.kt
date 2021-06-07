package com.sadri.universitypanel.domain.student.home.application

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun StudentHomeScreen(viewModel: StudentHomeViewModel) {
  val username = viewModel.viewState.observeAsState("").value
  Text(text = " Username : $username")
}
