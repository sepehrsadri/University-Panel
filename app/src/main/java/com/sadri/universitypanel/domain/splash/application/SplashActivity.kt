package com.sadri.universitypanel.domain.splash.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sadri.universitypanel.domain.login.application.LoginScreen
import com.sadri.universitypanel.domain.master.home.application.MasterHomeScreen
import com.sadri.universitypanel.domain.splash.core.model.SplashUserState
import com.sadri.universitypanel.domain.student.home.application.StudentHomeScreen
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
    content = {
      NavigationCoordinator(
        navController = navController,
        splashViewModel = splashViewModel,
        modifier = modifier
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
  NavHost(navController, startDestination = Screens.Splash.route) {
    composable(Screens.StudentHome.route) {
      StudentHomeScreen(
        modifier = modifier,
        viewModel = hiltViewModel(it),
        navController = navController
      )
    }
    composable(Screens.MasterHome.route) {
      MasterHomeScreen(
        modifier = modifier,
        viewModel = hiltViewModel(it)
      )
    }
    composable(Screens.Login.route) {
      LoginScreen(
        modifier = modifier,
        viewModel = hiltViewModel(it)
      )
    }
    composable(Screens.Splash.route) {
      ProgressBar()
    }
  }
  splashViewModel.viewState.observeAsState().value?.getContentIfNotHandled()?.let {
    val screen =
      when (it.userState) {
        SplashUserState.NOT_AUTHENTICATED -> Screens.Login
        SplashUserState.AUTHENTICATED_STUDENT -> Screens.StudentHome
        SplashUserState.AUTHENTICATED_MASTER -> Screens.MasterHome
      }
    navController.navigate(screen.route)
  }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
  return navController.currentBackStackEntry?.destination?.route
}

sealed class Screens(val route: String) {
  object StudentHome : Screens("StudentHome")
  object MasterHome : Screens("MasterHome")
  object Login : Screens("Login")
  object Splash : Screens("Splash")
}
