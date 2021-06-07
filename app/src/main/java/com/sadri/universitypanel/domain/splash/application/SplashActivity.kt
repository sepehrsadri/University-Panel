package com.sadri.universitypanel.domain.splash.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sadri.universitypanel.domain.student.home.application.StudentHomeScreen
import com.sadri.universitypanel.domain.student.home.application.StudentHomeViewModel
import com.sadri.universitypanel.domain.login.application.LoginScreen
import com.sadri.universitypanel.domain.login.application.LoginViewModel
import com.sadri.universitypanel.domain.master.home.application.MasterHomeScreen
import com.sadri.universitypanel.domain.master.home.application.MasterHomeViewModel
import com.sadri.universitypanel.domain.splash.core.model.SplashUserState
import com.sadri.universitypanel.infrastructure.ui.theme.UniversityPanelTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

  private val splashViewModel: SplashViewModel by viewModels()
  private val loginViewModel: LoginViewModel by viewModels()
  private val studentHomeViewModel: StudentHomeViewModel by viewModels()
  private val masterHomeViewModel: MasterHomeViewModel by viewModels()

  @ExperimentalMaterialApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Timber.d("WTF : onCreate ! ")
    setContent {
      UniversityPanelTheme {
        Content(
          splashViewModel = splashViewModel,
          loginViewModel = loginViewModel,
          studentHomeViewModel = studentHomeViewModel,
          masterHomeViewModel = masterHomeViewModel
        )
      }
    }
  }
}

@ExperimentalMaterialApi
@Composable
fun Content(
  splashViewModel: SplashViewModel,
  loginViewModel: LoginViewModel,
  studentHomeViewModel: StudentHomeViewModel,
  masterHomeViewModel: MasterHomeViewModel,
  modifier: Modifier = Modifier
) {
  val navController = rememberNavController()
  splashViewModel.viewState.observeAsState().value?.getContentIfNotHandled()?.let {
    Timber.d("WTF splashView State called $it")
    val currentRoute = currentRoute(navController) ?: return
    Timber.d("WTF passed current route ! ")

    val screen =
      when (it.userState) {
        SplashUserState.LOADING -> Screens.Splash
        SplashUserState.NOT_AUTHENTICATED -> Screens.Login
        SplashUserState.AUTHENTICATED_STUDENT -> Screens.StudentHome
        SplashUserState.AUTHENTICATED_MASTER -> Screens.MasterHome
      }


    if (currentRoute != screen.route) {
      navController.navigate(screen.route) {
        launchSingleTop = true
      }
    }
  }

  Scaffold(
    content = {
      NavigationCoordinator(
        loginViewModel = loginViewModel,
        navController = navController,
        studentHomeViewModel = studentHomeViewModel,
        masterHomeViewModel = masterHomeViewModel,
        modifier = modifier
      )
    }
  )
}

@ExperimentalMaterialApi
@Composable
fun NavigationCoordinator(
  navController: NavHostController,
  loginViewModel: LoginViewModel,
  studentHomeViewModel: StudentHomeViewModel,
  masterHomeViewModel: MasterHomeViewModel,
  modifier: Modifier
) {
  NavHost(navController, startDestination = Screens.Splash.route) {
    composable(Screens.StudentHome.route) {
      StudentHomeScreen(studentHomeViewModel)
    }
    composable(Screens.MasterHome.route) {
      MasterHomeScreen(masterHomeViewModel)
    }
    composable(Screens.Login.route) {
      LoginScreen(loginViewModel, modifier)
    }
    composable(Screens.Splash.route) {
      SplashScreen()
    }
  }
}

@Composable
fun SplashScreen() {
  Text(text = "Splash Screen")
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
