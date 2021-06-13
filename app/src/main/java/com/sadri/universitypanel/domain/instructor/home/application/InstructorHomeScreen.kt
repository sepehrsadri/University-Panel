package com.sadri.universitypanel.domain.instructor.home.application

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sadri.universitypanel.domain.instructor.home.core.model.InstructorSectionResponse
import com.sadri.universitypanel.domain.login.core.model.ToastViewState
import com.sadri.universitypanel.domain.splash.application.Screens
import com.sadri.universitypanel.infrastructure.ui.LogoutBottomSheetContent
import com.sadri.universitypanel.infrastructure.ui.ProfileTopAppBarWithBottomSheet
import com.sadri.universitypanel.infrastructure.ui.ProgressBar
import com.sadri.universitypanel.infrastructure.ui.SnackBar
import com.sadri.universitypanel.infrastructure.ui.theme.Shapes
import com.sadri.universitypanel.infrastructure.utils.isFalseOrNull

@ExperimentalMaterialApi
@Composable
fun InstructorHomeScreen(
  modifier: Modifier,
  viewModel: InstructorHomeViewModel,
  navController: NavController
) {
  val viewState = viewModel.viewState.observeAsState().value!!
  val isLoading = viewModel.loading.observeAsState().value

  viewModel.error.observeAsState().value?.getContentIfNotHandled()?.let {
    if (it is ToastViewState.Show) {
      SnackBar(modifier = modifier, text = it.text, dismiss = { viewModel.dismissToast() })
    }
  }

  if (isLoading.isFalseOrNull().not()) {
    ProgressBar()
  }

  val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
    bottomSheetState = rememberBottomSheetState(
      initialValue = BottomSheetValue.Collapsed
    )
  )

  val coroutineScope = rememberCoroutineScope()
  val scrollState = rememberLazyListState()

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
      ProfileTopAppBarWithBottomSheet(
        username = viewState.name,
        coroutineScope = coroutineScope,
        bottomSheetScaffoldState = bottomSheetScaffoldState
      )
    },
    content = {
      SectionsList(
        sections = viewState.sections,
        state = scrollState,
        onItemClick = { id ->
          navController.navigate(Screens.InstructorSection.route.plus("/$id"))
        }
      )
    }
  )
}


@Composable
fun SectionsList(
  sections: List<InstructorSectionResponse>,
  state: LazyListState,
  modifier: Modifier = Modifier,
  onItemClick: (Int) -> Unit
) {
  LazyColumn(modifier = modifier, state = state) {
    items(sections.size) { index ->
      ListItem(
        section = sections[index],
        modifier = modifier,
        onClick = onItemClick
      )
      Divider()
    }
  }
}

@Composable
fun ListItem(
  section: InstructorSectionResponse,
  modifier: Modifier = Modifier,
  onClick: (Int) -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .padding(16.dp)
      .fillMaxWidth()
      .clickable { onClick(section.id) }
  ) {
    Spacer(Modifier.width(10.dp))
    Text(
      text = section.title
    )
  }
}