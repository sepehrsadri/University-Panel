package com.sadri.universitypanel.infrastructure.di

import android.content.Context
import com.sadri.universitypanel.domain.home.core.HomeFacade
import com.sadri.universitypanel.domain.home.core.ports.incoming.GetAuthenticatedUserInfo
import com.sadri.universitypanel.domain.home.core.ports.outgoing.ReadUserInfoDatabase
import com.sadri.universitypanel.domain.login.core.LoginFacade
import com.sadri.universitypanel.domain.login.core.ports.incoming.OnUserAuthenticate
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SaveUserAuthenticationDatabase
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SendAuthenticateUserRequest
import com.sadri.universitypanel.domain.login.infrastructure.AuthenticationDataSource
import com.sadri.universitypanel.domain.login.infrastructure.AuthenticationDataSourceImpl
import com.sadri.universitypanel.domain.splash.core.SplashFacade
import com.sadri.universitypanel.domain.splash.core.ports.incoming.GetUserAuthenticationState
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthenticationDatabase
import com.sadri.universitypanel.domain.splash.infrastructure.UserAuthenticationDatabaseAdapter
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
    return UserAuthenticationDatabaseAdapter(context)
  }

  @Provides
  @Singleton
  fun provideSaveUserAuthenticationDatabase(@ApplicationContext context: Context): SaveUserAuthenticationDatabase {
    return UserAuthenticationDatabaseAdapter(context)
  }

  @Provides
  @Singleton
  fun provideReadUserInfoDatabase(@ApplicationContext context: Context): ReadUserInfoDatabase {
    return UserAuthenticationDatabaseAdapter(context)
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
  fun provideGetAuthenticatedUserInfo(readUserInfoDatabase: ReadUserInfoDatabase): GetAuthenticatedUserInfo {
    return HomeFacade(readUserInfoDatabase)
  }

  @Provides
  @Singleton
  fun provideSendAuthenticateUserRequest(retrofit: Retrofit): SendAuthenticateUserRequest {
    return AuthenticationDataSourceImpl(
      NetworkModule.provideService(retrofit, AuthenticationDataSource::class.java)
    )
  }
}