package com.sadri.universitypanel.domain.login.application

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadri.universitypanel.R
import com.sadri.universitypanel.domain.login.core.model.UserRule
import com.sadri.universitypanel.domain.login.core.model.UserRuleChipState
import kotlinx.coroutines.launch

val ruleChipsList = listOf(
  UserRuleChipState.Student,
  UserRuleChipState.Instructor
)

@ExperimentalMaterialApi
@Composable
fun LoginScreen(
  modifier: Modifier = Modifier,
  viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
  val viewState = viewModel.viewState.observeAsState().value!!
  val messageState = viewModel.message.observeAsState().value!!

  val snackBarHostState = remember { SnackbarHostState() }
  val coroutineScope = rememberCoroutineScope()

  Scaffold(
    scaffoldState = rememberScaffoldState(snackbarHostState = snackBarHostState),
  ) { innerPadding ->
    if (messageState.isNotEmpty()) {
      coroutineScope.launch {
        snackBarHostState.showSnackbar(
          message = messageState,
          actionLabel = "Dismiss"
        )
        viewModel.dismissSnackBar()
      }
    }
    LoginContent(
      modifier = Modifier.padding(innerPadding),
      number = viewState.number,
      password = viewState.password,
      rule = viewState.rule,
      onNumberChanged = { viewModel.onNumberChanged(it) },
      onPasswordChanged = { viewModel.onPasswordChanged(it) },
      onRuleChanged = { viewModel.onRuleChanged(it) }) {
      viewModel.submit()
    }
  }
}

@ExperimentalMaterialApi
@Composable
fun LoginContent(
  modifier: Modifier,
  number: String,
  password: String,
  rule: UserRule,
  onNumberChanged: (String) -> Unit,
  onPasswordChanged: (String) -> Unit,
  onRuleChanged: (UserRule) -> Unit,
  submit: () -> Unit
) {
  Column(
    modifier = modifier
      .padding(16.dp)
      .fillMaxSize()
      .padding(12.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Card(
      modifier = modifier
        .padding(bottom = 16.dp)
        .requiredSize(150.dp),
      shape = CircleShape,
      elevation = 2.dp
    ) {
      Image(
        painterResource(R.drawable.login_icon),
        contentDescription = "",
        contentScale = ContentScale.Crop
      )
    }

    OutlinedTextField(
      value = number,
      onValueChange = { onNumberChanged(it) },
      label = { Text(text = "ID") },
      modifier = modifier.padding(8.dp),
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next
      ),
    )

    OutlinedTextField(
      value = password,
      onValueChange = { onPasswordChanged(it) },
      label = { Text(text = "Password") },
      modifier = modifier.padding(8.dp)
    )

    RuleChips(
      selectedRule = rule,
      onRuleChanged = { onRuleChanged(it) },
      userRuleChipsList = ruleChipsList,
      modifier = modifier
    )

    Button(
      onClick = { submit() },
      modifier = modifier
        .padding(top = 32.dp)
    ) {
      Text(
        text = "Submit",
        style = MaterialTheme.typography.h6
      )
    }
  }
}

@ExperimentalMaterialApi
@Composable
fun RuleChips(
  selectedRule: UserRule,
  onRuleChanged: (UserRule) -> Unit,
  userRuleChipsList: List<UserRuleChipState>,
  modifier: Modifier
) {
  Row(
    modifier = modifier.padding(12.dp)
  ) {
    userRuleChipsList.forEachIndexed { _, rule ->
      Card(
        onClick = { onRuleChanged(rule.userRule) },
        backgroundColor = Color.White,
        shape = RoundedCornerShape(15.dp),
        modifier = modifier.padding(8.dp),
        content = {
          Text(
            text = if (selectedRule == rule.userRule) "✓ ${rule.userRule.name}" else rule.userRule.name,
            color = MaterialTheme.colors.primary,
            modifier = modifier.padding(8.dp)
          )
        }
      )
    }
  }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun LoginScreenPreview() {
  LoginScreen()
}
