package com.sadri.universitypanel.domain.home.application

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.sadri.universitypanel.infrastructure.ui.theme.UniversityPanelTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
  val username = viewModel.viewState.observeAsState("").value
  Text(text = " Username : $username")
}
