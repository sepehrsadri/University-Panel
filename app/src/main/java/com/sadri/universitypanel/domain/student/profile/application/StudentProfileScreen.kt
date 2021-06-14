package com.sadri.universitypanel.domain.student.profile.application

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sadri.universitypanel.domain.splash.application.Screens
import com.sadri.universitypanel.infrastructure.ui.LogoutBottomSheetContent
import com.sadri.universitypanel.infrastructure.ui.ProgressBar
import com.sadri.universitypanel.infrastructure.ui.theme.Shapes
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun StudentProfileScreen(
  modifier: Modifier,
  viewModel: StudentProfileViewModel,
  navController: NavHostController
) {
  val viewState = viewModel.viewState.observeAsState().value!!

  val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
    bottomSheetState = rememberBottomSheetState(
      initialValue = BottomSheetValue.Collapsed
    )
  )
  val coroutineScope = rememberCoroutineScope()

  BottomSheetScaffold(
    scaffoldState = bottomSheetScaffoldState,
    sheetBackgroundColor = MaterialTheme.colors.primary,
    sheetShape = Shapes.large,
    sheetContent = {
      LogoutBottomSheetContent(
        modifier = modifier,
        coroutineScope = coroutineScope,
        bottomSheetScaffoldState = bottomSheetScaffoldState
      ) {
        viewModel.logout()
        navController.navigate(Screens.Login.route)
      }
    },
    sheetPeekHeight = 0.dp,
    content = {
      Column(
        modifier = modifier
          .padding(16.dp)
          .fillMaxSize()
          .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        if (viewState.isLoading) {
          ProgressBar(modifier)
        }
        Text(
          text = "Name : ${viewState.name}",
          modifier = modifier.padding(4.dp),
          style = MaterialTheme.typography.h6
        )
        Text(
          text = "Number : ${viewState.number}",
          modifier = modifier.padding(4.dp),
          style = MaterialTheme.typography.h6
        )
        Card(
          modifier = modifier.padding(bottom = 32.dp),
          backgroundColor = MaterialTheme.colors.primary,
          elevation = 2.dp
        ) {
          IconButton(
            onClick = {
              coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.expand()
              }
            },
          ) {
            Icon(
              Icons.Filled.Home,
              "",
              tint = Color.White
            )
          }
        }
      }
    },
  )
}