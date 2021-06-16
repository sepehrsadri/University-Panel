package com.sadri.universitypanel.domain.student.home.application

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sadri.universitypanel.R
import com.sadri.universitypanel.domain.student.home.core.model.StudentCourseResponse
import com.sadri.universitypanel.domain.student.profile.application.StudentProfileScreen
import com.sadri.universitypanel.domain.student.semester.application.StudentSemesterScreen
import com.sadri.universitypanel.infrastructure.ui.ProgressBar
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun StudentScreen(
  modifier: Modifier,
  mainNavController: NavHostController
) {
  val studentGraphNavController = rememberNavController()

  val bottomNavigationItems = listOf(
    StudentScreens.StudentHome,
    StudentScreens.StudentSemester,
    StudentScreens.StudentProfile,
  )

  Scaffold(
    bottomBar = {
      StudentBottomNavigation(
        navController = studentGraphNavController,
        items = bottomNavigationItems
      )
    }
  ) {
    StudentNavigationCoordinator(
      studentNavController = studentGraphNavController,
      mainNavController = mainNavController,
      modifier = modifier
    )
  }

}

@Composable
fun StudentBottomNavigation(
  navController: NavHostController,
  items: List<StudentScreens>
) {
  BottomNavigation {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    items.forEach { screen ->
      BottomNavigationItem(
        icon = { Icon(screen.icon, contentDescription = null) },
        label = { Text(stringResource(id = screen.resourceId)) },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = {
          // This if check gives us a "singleTop" behavior where we do not create a
          // second instance of the composable if we are already on that destination
          navController.navigate(screen.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
              saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
          }
        }
      )
    }
  }
}

@ExperimentalMaterialApi
@Composable
fun StudentNavigationCoordinator(
  studentNavController: NavHostController,
  mainNavController: NavHostController,
  modifier: Modifier
) {
  NavHost(
    studentNavController,
    startDestination = StudentScreens.StudentHome.route,
    modifier = modifier
  ) {
    composable(StudentScreens.StudentHome.route) {
      StudentHomeScreen(
        modifier = modifier,
        viewModel = hiltViewModel()
      )
    }
    composable(StudentScreens.StudentSemester.route) {
      StudentSemesterScreen(
        modifier = modifier,
        viewModel = hiltViewModel()
      )
    }
    composable(StudentScreens.StudentProfile.route) {
      StudentProfileScreen(
        modifier = modifier,
        viewModel = hiltViewModel(),
        navController = mainNavController
      )
    }
  }
}

@ExperimentalMaterialApi
@Composable
fun StudentHomeScreen(
  modifier: Modifier,
  viewModel: StudentHomeViewModel
) {
  val viewState = viewModel.viewState.observeAsState().value!!
  val messageState = viewModel.message.observeAsState().value!!

  val scrollState = rememberLazyListState()
  val snackBarHostState = remember { SnackbarHostState() }
  val coroutineScope = rememberCoroutineScope()

  Scaffold(
    scaffoldState = rememberScaffoldState(snackbarHostState = snackBarHostState)
  ) {
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
    CoursesList(
      modifier = modifier,
      courses = viewState.courses,
      state = scrollState
    )
  }
}

@Composable
fun CoursesList(
  courses: List<StudentCourseResponse>,
  state: LazyListState,
  modifier: Modifier = Modifier
) {
  LazyColumn(modifier = modifier, state = state) {
    items(courses.size) { index ->
      ListItem(
        studentCourse = courses[index],
        modifier = modifier
      )
      Divider()
    }
  }
}

@Composable
fun ListItem(
  studentCourse: StudentCourseResponse,
  modifier: Modifier = Modifier
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .padding(16.dp)
      .fillMaxWidth()
  ) {
    Spacer(modifier.width(10.dp))
    Column {
      Text(
        text = "Title : ${studentCourse.title}",
        style = MaterialTheme.typography.h6
      )
      Text(
        text = "Semester : ${studentCourse.semester}",
        style = MaterialTheme.typography.subtitle2
      )
      Text(
        text = "Grade : ${studentCourse.grade ?: "--"}",
        style = MaterialTheme.typography.subtitle2
      )
      Text(
        text = "Credits : ${studentCourse.credits}",
        style = MaterialTheme.typography.subtitle2
      )
    }
  }
}

sealed class StudentScreens(
  val route: String,
  @StringRes val resourceId: Int,
  val icon: ImageVector
) {
  object StudentHome : StudentScreens(
    route = "StudentHome",
    resourceId = R.string.student_home_title,
    icon = Icons.Filled.Home
  )

  object StudentSemester : StudentScreens(
    "StudentSemester",
    resourceId = R.string.student_semester_title,
    icon = Icons.Filled.Check
  )

  object StudentProfile : StudentScreens(
    "StudentProfile",
    resourceId = R.string.student_profile,
    icon = Icons.Filled.Person
  )
}