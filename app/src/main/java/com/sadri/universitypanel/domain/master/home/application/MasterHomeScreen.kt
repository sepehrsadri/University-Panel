package com.sadri.universitypanel.domain.master.home.application

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun MasterHomeScreen(viewModel: MasterHomeViewModel) {
  val username = viewModel.viewState.observeAsState("").value
  Text(text = " Master Name : $username")
}