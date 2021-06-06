package com.sadri.universitypanel.domain.login.application

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.sadri.universitypanel.infrastructure.ui.theme.UniversityPanelTheme

@Composable
fun LoginScreen(viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
  val number = viewModel.number.observeAsState("12345").value
  val password = viewModel.password.observeAsState(initial = "admin").value

  Column {
    OutlinedTextField(
      value = number,
      onValueChange = { viewModel.onNumberChanged(it) },
      label = { Text(text = "Number") }
    )

    OutlinedTextField(
      value = password,
      onValueChange = { viewModel.onPasswordChanged(it) },
      label = { Text(text = "Password") }
    )

    Button(onClick = { viewModel.submit() }) {
      Text(text = "Submit")
    }

  }
}
