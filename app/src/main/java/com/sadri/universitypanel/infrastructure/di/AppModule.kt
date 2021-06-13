package com.sadri.universitypanel.infrastructure.di

import android.content.Context
import com.sadri.universitypanel.domain.instructor.home.core.InstructorFacade
import com.sadri.universitypanel.domain.instructor.home.core.ports.incoming.RetrieveInstructorSections
import com.sadri.universitypanel.domain.instructor.home.core.ports.incoming.RetrieveSectionStudents
import com.sadri.universitypanel.domain.instructor.home.core.ports.outgoing.RequestInstructorSections
import com.sadri.universitypanel.domain.instructor.home.core.ports.outgoing.RequestSectionStudents
import com.sadri.universitypanel.domain.instructor.home.infrastructure.InstructorDataSource
import com.sadri.universitypanel.domain.instructor.home.infrastructure.InstructorDataSourceImpl
import com.sadri.universitypanel.domain.login.core.LoginFacade
import com.sadri.universitypanel.domain.login.core.ports.incoming.OnUserAuthenticate
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SaveUserAuthenticationDatabase
import com.sadri.universitypanel.domain.login.core.ports.outgoing.SendAuthenticateUserRequest
import com.sadri.universitypanel.domain.splash.core.SplashFacade
import com.sadri.universitypanel.domain.splash.core.ports.incoming.AuthStateChanged
import com.sadri.universitypanel.domain.splash.core.ports.incoming.GetUserAuthenticationState
import com.sadri.universitypanel.domain.splash.core.ports.incoming.RequestLogout
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.ClearUserInfoDatabase
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthProvider
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthenticationDatabase
import com.sadri.universitypanel.domain.splash.infrastructure.AuthenticatedStudentAuthenticationDatabaseAdapter
import com.sadri.universitypanel.domain.splash.infrastructure.UserAuthProviderImpl
import com.sadri.universitypanel.domain.student.home.core.StudentFacade
import com.sadri.universitypanel.domain.student.home.core.ports.incoming.RetrieveAuthenticatedStudentInfo
import com.sadri.universitypanel.domain.student.home.core.ports.incoming.RetrieveStudentCourses
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.ReadAuthenticatedStudentInfoDatabase
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.RequestStudentCourses
import com.sadri.universitypanel.domain.student.home.infrastructure.StudentDataSource
import com.sadri.universitypanel.domain.student.home.infrastructure.StudentDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
  fun provideClearUserInfoDatabase(@ApplicationContext context: Context): ClearUserInfoDatabase {
    return AuthenticatedStudentAuthenticationDatabaseAdapter(context)
  }

  @Provides
  @Singleton
  fun provideAuthStateChanged(readAuthenticatedStudentInfoDatabase: ReadAuthenticatedStudentInfoDatabase): AuthStateChanged {
    return UserAuthProviderImpl(readAuthenticatedStudentInfoDatabase)
  }

  @Provides
  @Singleton
  fun provideUserAuthProvider(readAuthenticatedStudentInfoDatabase: ReadAuthenticatedStudentInfoDatabase): UserAuthProvider {
    return UserAuthProviderImpl(readAuthenticatedStudentInfoDatabase)
  }

  @Provides
  @Singleton
  fun provideOnUserAuthenticate(
    sendAuthenticateUserRequest: SendAuthenticateUserRequest,
    saveUserAuthenticationDatabase: SaveUserAuthenticationDatabase,
    clearUserInfoDatabase: ClearUserInfoDatabase,
    authStateChanged: AuthStateChanged
  ): OnUserAuthenticate {
    return LoginFacade(
      sendAuthenticateUserRequest = sendAuthenticateUserRequest,
      saveUserAuthenticationDatabase = saveUserAuthenticationDatabase,
      authStateChanged = authStateChanged,
      clearUserInfoDatabase = clearUserInfoDatabase
    )
  }

  @Provides
  @Singleton
  fun provideRequestLogout(
    sendAuthenticateUserRequest: SendAuthenticateUserRequest,
    saveUserAuthenticationDatabase: SaveUserAuthenticationDatabase,
    clearUserInfoDatabase: ClearUserInfoDatabase,
    authStateChanged: AuthStateChanged
  ): RequestLogout {
    return LoginFacade(
      sendAuthenticateUserRequest = sendAuthenticateUserRequest,
      saveUserAuthenticationDatabase = saveUserAuthenticationDatabase,
      authStateChanged = authStateChanged,
      clearUserInfoDatabase = clearUserInfoDatabase
    )
  }

  @Provides
  @Singleton
  fun provideRetrieveInstructorSections(
    requestInstructorSections: RequestInstructorSections,
    requestSectionStudents: RequestSectionStudents
  ): RetrieveInstructorSections {
    return InstructorFacade(
      requestInstructorSections = requestInstructorSections,
      requestSectionStudents = requestSectionStudents
    )
  }

  @Provides
  @Singleton
  fun provideRetrieveSectionStudents(
    requestInstructorSections: RequestInstructorSections,
    requestSectionStudents: RequestSectionStudents
  ): RetrieveSectionStudents {
    return InstructorFacade(
      requestInstructorSections = requestInstructorSections,
      requestSectionStudents = requestSectionStudents
    )
  }

  @Provides
  @Singleton
  fun provideRetrieveAuthenticatedStudentInfo(
    readAuthenticatedStudentInfoDatabase: ReadAuthenticatedStudentInfoDatabase,
    requestStudentCourses: RequestStudentCourses
  ): RetrieveAuthenticatedStudentInfo {
    return StudentFacade(readAuthenticatedStudentInfoDatabase, requestStudentCourses)
  }

  @Provides
  @Singleton
  fun provideRetrieveStudentCourses(
    readAuthenticatedStudentInfoDatabase: ReadAuthenticatedStudentInfoDatabase,
    requestStudentCourses: RequestStudentCourses
  ): RetrieveStudentCourses {
    return StudentFacade(readAuthenticatedStudentInfoDatabase, requestStudentCourses)
  }

  @Provides
  @Singleton
  fun provideRequestStudentCourses(studentDataSource: StudentDataSource): RequestStudentCourses {
    return StudentDataSourceImpl(studentDataSource)
  }

  @Provides
  @Singleton
  fun provideRequestInstructorSections(instructorDataSource: InstructorDataSource): RequestInstructorSections {
    return InstructorDataSourceImpl(instructorDataSource)
  }

  @Provides
  @Singleton
  fun provideRequestSectionStudents(instructorDataSource: InstructorDataSource): RequestSectionStudents {
    return InstructorDataSourceImpl(instructorDataSource)
  }
}