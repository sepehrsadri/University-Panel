package com.sadri.universitypanel.infrastructure.di

import android.content.Context
import com.sadri.universitypanel.domain.student.home.core.StudentHomeFacade
import com.sadri.universitypanel.domain.student.home.core.ports.incoming.GetAuthenticatedStudentInfo
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.ReadAuthenticatedStudentInfoDatabase
import com.sadri.universitypanel.domain.login.core.LoginFacade
import com.sadri.universitypanel.domain.login.core.ports.incoming.OnUserAuthenticate
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SaveUserAuthenticationDatabase
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SendAuthenticateUserRequest
import com.sadri.universitypanel.domain.login.infrastructure.AuthenticationDataSource
import com.sadri.universitypanel.domain.login.infrastructure.AuthenticationDataSourceImpl
import com.sadri.universitypanel.domain.splash.core.SplashFacade
import com.sadri.universitypanel.domain.splash.core.ports.incoming.GetUserAuthenticationState
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthenticationDatabase
import com.sadri.universitypanel.domain.splash.infrastructure.AuthenticatedStudentAuthenticationDatabaseAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Provides
  @Singleton
  fun provideUserAuthenticationDatabase(userAuthenticationDatabase: UserAuthenticationDatabase): GetUserAuthenticationState {
    return SplashFacade(userAuthenticationDatabase)
  }

  @Provides
  @Singleton
  fun provideUserAuthenticationDatabaseAdapter(@ApplicationContext context: Context): UserAuthenticationDatabase {
    return AuthenticatedStudentAuthenticationDatabaseAdapter(context)
  }

  @Provides
  @Singleton
  fun provideSaveUserAuthenticationDatabase(@ApplicationContext context: Context): SaveUserAuthenticationDatabase {
    return AuthenticatedStudentAuthenticationDatabaseAdapter(context)
  }

  @Provides
  @Singleton
  fun provideReadUserInfoDatabase(@ApplicationContext context: Context): ReadAuthenticatedStudentInfoDatabase {
    return AuthenticatedStudentAuthenticationDatabaseAdapter(context)
  }

  @Provides
  @Singleton
  fun provideOnUserAuthenticate(
    sendAuthenticateUserRequest: SendAuthenticateUserRequest,
    saveUserAuthenticationDatabase: SaveUserAuthenticationDatabase
  ): OnUserAuthenticate {
    return LoginFacade(
      sendAuthenticateUserRequest,
      saveUserAuthenticationDatabase
    )
  }

  @Provides
  @Singleton
  fun provideGetAuthenticatedUserInfo(readAuthenticatedStudentInfoDatabase: ReadAuthenticatedStudentInfoDatabase): GetAuthenticatedStudentInfo {
    return StudentHomeFacade(readAuthenticatedStudentInfoDatabase)
  }

  @Provides
  @Singleton
  fun provideSendAuthenticateUserRequest(retrofit: Retrofit): SendAuthenticateUserRequest {
    return AuthenticationDataSourceImpl(
      NetworkModule.provideService(retrofit, AuthenticationDataSource::class.java)
    )
  }
}