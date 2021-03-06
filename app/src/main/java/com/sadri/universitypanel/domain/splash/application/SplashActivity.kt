package com.sadri.universitypanel.domain.splash.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.sadri.universitypanel.domain.instructor.grade.application.InstructorGradeScreen
import com.sadri.universitypanel.domain.instructor.home.application.InstructorHomeScreen
import com.sadri.universitypanel.domain.login.application.LoginScreen
import com.sadri.universitypanel.domain.splash.core.model.SplashUserState
import com.sadri.universitypanel.domain.student.home.application.StudentScreen
import com.sadri.universitypanel.infrastructure.ui.ProgressBar
import com.sadri.universitypanel.infrastructure.ui.theme.UniversityPanelTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

  private val splashViewModel: SplashViewModel by viewModels()

  @ExperimentalMaterialApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      UniversityPanelTheme {
        Content(splashViewModel = splashViewModel)
      }
    }
  }
}

@ExperimentalMaterialApi
@Composable
fun Content(
  modifier: Modifier = Modifier,
  splashViewModel: SplashViewModel
) {
  val navController = rememberNavController().apply {
    enableOnBackPressed(false)
  }
  Scaffold(
    content = { innerPadding ->
      NavigationCoordinator(
        navController = navController,
        splashViewModel = splashViewModel,
        modifier = modifier.padding(innerPadding)
      )
    }
  )
}

@ExperimentalMaterialApi
@Composable
fun NavigationCoordinator(
  navController: NavHostController,
  splashViewModel: SplashViewModel,
  modifier: Modifier
) {
  NavHost(
    navController,
    startDestination = Screens.Splash.route,
    modifier = modifier
  ) {
    composable(Screens.Student.route) {
      StudentScreen(
        modifier = modifier,
        mainNavController = navController
      )
    }
    composable(Screens.InstructorHome.route) {
      InstructorHomeScreen(
        modifier = modifier,
        viewModel = hiltViewModel(),
        navController = navController
      )
    }
    composable(
      route = Screens.InstructorGrade.route.plus("/{$INSTRUCTOR_GRADE_SCREEN_ARGUMENT_ID_KEY}"),
      arguments = listOf(navArgument(INSTRUCTOR_GRADE_SCREEN_ARGUMENT_ID_KEY) {
        type = NavType.IntType
      })
    ) {
      InstructorGradeScreen(
        modifier = modifier,
        viewModel = hiltViewModel(),
        navController = navController
      )
    }
    composable(Screens.Login.route) {
      LoginScreen(
        modifier = modifier,
        viewModel = hiltViewModel()
      )
    }
    composable(Screens.Splash.route) {
      ProgressBar(modifier)
    }
  }
  splashViewModel.viewState.observeAsState().value?.getContentIfNotHandled()?.let {
    val screen =
      when (it.userState) {
        SplashUserState.NOT_AUTHENTICATED -> Screens.Login
        SplashUserState.AUTHENTICATED_STUDENT -> Screens.Student
        SplashUserState.AUTHENTICATED_INSTRUCTOR -> Screens.InstructorHome
      }
    navController.navigate(screen.route)
  }
}

sealed class Screens(val route: String) {
  object Student : Screens("Student")
  object InstructorHome : Screens("InstructorHome")
  object InstructorGrade : Screens("InstructorGrade")
  object Login : Screens("Login")
  object Splash : Screens("Splash")
}

const val INSTRUCTOR_GRADE_SCREEN_ARGUMENT_ID_KEY = "section_id"
