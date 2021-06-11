package com.sadri.universitypanel.domain.master.home.application

import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sadri.universitypanel.domain.login.core.model.ToastViewState
import com.sadri.universitypanel.domain.splash.application.Screens
import com.sadri.universitypanel.infrastructure.ui.LogoutBottomSheetContent
import com.sadri.universitypanel.infrastructure.ui.ProfileTopAppBar
import com.sadri.universitypanel.infrastructure.ui.ProgressBar
import com.sadri.universitypanel.infrastructure.ui.SnackBar
import com.sadri.universitypanel.infrastructure.ui.theme.Shapes

@ExperimentalMaterialApi
@Composable
fun MasterHomeScreen(
  modifier: Modifier,
  viewModel: MasterHomeViewModel,
  navController: NavController
) {
  val viewState = viewModel.viewState.observeAsState().value!!

  viewModel.error.observeAsState().value?.getContentIfNotHandled()?.let {
    if (it is ToastViewState.Show) {
      SnackBar(modifier = modifier, text = it.text, dismiss = { viewModel.dismissToast() })
    }
  }

  if (viewState.isLoading) {
    ProgressBar()
  }

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
    topBar = {
      ProfileTopAppBar(
        username = viewState.name,
        coroutineScope = coroutineScope,
        bottomSheetScaffoldState = bottomSheetScaffoldState
      )
    },
    content = {
    }
  )
}