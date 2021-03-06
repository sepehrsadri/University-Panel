package com.sadri.universitypanel.infrastructure.di

import android.content.Context
import com.sadri.universitypanel.domain.instructor.grade.core.ports.incoming.SubmitStudentsGrade
import com.sadri.universitypanel.domain.instructor.grade.core.ports.outgoing.RequestSendStudentsGrade
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
import com.sadri.universitypanel.domain.splash.core.ports.incoming.RetrieveUserInfo
import com.sadri.universitypanel.domain.splash.core.ports.incoming.RetrieveUserToken
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.ClearUserInfoDatabase
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.ReadUserInfoDatabase
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.ReadUserTokenDatabase
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthProvider
import com.sadri.universitypanel.domain.splash.core.ports.outgoing.UserAuthenticationDatabase
import com.sadri.universitypanel.domain.splash.infrastructure.UserAuthProviderImpl
import com.sadri.universitypanel.domain.splash.infrastructure.UserDatabaseAdapter
import com.sadri.universitypanel.domain.student.home.core.StudentFacade
import com.sadri.universitypanel.domain.student.home.core.ports.incoming.RetrieveStudentCourses
import com.sadri.universitypanel.domain.student.home.core.ports.outgoing.RequestStudentCourses
import com.sadri.universitypanel.domain.student.home.infrastructure.StudentDataSource
import com.sadri.universitypanel.domain.student.home.infrastructure.StudentDataSourceImpl
import com.sadri.universitypanel.domain.student.semester.core.ports.incoming.RetrieveStudentSemester
import com.sadri.universitypanel.domain.student.semester.core.ports.incoming.SubmitStudentTakes
import com.sadri.universitypanel.domain.student.semester.core.ports.outgoing.RequestSendStudentTakes
import com.sadri.universitypanel.domain.student.semester.core.ports.outgoing.RequestStudentSemester
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
  fun provideGetUserAuthenticationState(
    userAuthenticationDatabase: UserAuthenticationDatabase,
    readUserInfoDatabase: ReadUserInfoDatabase,
    readUserTokenDatabase: ReadUserTokenDatabase
  ): GetUserAuthenticationState {
    return SplashFacade(
      userAuthenticationDatabase = userAuthenticationDatabase,
      readUserInfoDatabase = readUserInfoDatabase,
      readUserTokenDatabase = readUserTokenDatabase
    )
  }

  @Provides
  @Singleton
  fun provideRetrieveUserInfo(
    userAuthenticationDatabase: UserAuthenticationDatabase,
    readUserInfoDatabase: ReadUserInfoDatabase,
    readUserTokenDatabase: ReadUserTokenDatabase
  ): RetrieveUserInfo {
    return SplashFacade(
      userAuthenticationDatabase = userAuthenticationDatabase,
      readUserInfoDatabase = readUserInfoDatabase,
      readUserTokenDatabase = readUserTokenDatabase
    )
  }

  @Provides
  @Singleton
  fun provideRetrieveUserToken(
    userAuthenticationDatabase: UserAuthenticationDatabase,
    readUserInfoDatabase: ReadUserInfoDatabase,
    readUserTokenDatabase: ReadUserTokenDatabase
  ): RetrieveUserToken {
    return SplashFacade(
      userAuthenticationDatabase = userAuthenticationDatabase,
      readUserInfoDatabase = readUserInfoDatabase,
      readUserTokenDatabase = readUserTokenDatabase
    )
  }

  @Provides
  @Singleton
  fun provideUserAuthenticationDatabaseAdapter(@ApplicationContext context: Context): UserAuthenticationDatabase {
    return UserDatabaseAdapter(context)
  }

  @Provides
  @Singleton
  fun provideSaveUserAuthenticationDatabase(@ApplicationContext context: Context): SaveUserAuthenticationDatabase {
    return UserDatabaseAdapter(context)
  }

  @Provides
  @Singleton
  fun provideReadUserInfoDatabase(@ApplicationContext context: Context): ReadUserInfoDatabase {
    return UserDatabaseAdapter(context)
  }

  @Provides
  @Singleton
  fun provideReadUserTokenDatabase(@ApplicationContext context: Context): ReadUserTokenDatabase {
    return UserDatabaseAdapter(context)
  }

  @Provides
  @Singleton
  fun provideClearUserInfoDatabase(@ApplicationContext context: Context): ClearUserInfoDatabase {
    return UserDatabaseAdapter(context)
  }

  @Provides
  @Singleton
  fun provideAuthStateChanged(retrieveUserToken: RetrieveUserToken): AuthStateChanged {
    return UserAuthProviderImpl(retrieveUserToken)
  }

  @Provides
  @Singleton
  fun provideUserAuthProvider(retrieveUserToken: RetrieveUserToken): UserAuthProvider {
    return UserAuthProviderImpl(retrieveUserToken)
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
    requestSectionStudents: RequestSectionStudents,
    requestSendStudentsGrade: RequestSendStudentsGrade
  ): RetrieveInstructorSections {
    return InstructorFacade(
      requestInstructorSections = requestInstructorSections,
      requestSectionStudents = requestSectionStudents,
      requestSendStudentsGrade = requestSendStudentsGrade
    )
  }

  @Provides
  @Singleton
  fun provideSubmitStudentsGrade(
    requestInstructorSections: RequestInstructorSections,
    requestSectionStudents: RequestSectionStudents,
    requestSendStudentsGrade: RequestSendStudentsGrade
  ): SubmitStudentsGrade {
    return InstructorFacade(
      requestInstructorSections = requestInstructorSections,
      requestSectionStudents = requestSectionStudents,
      requestSendStudentsGrade = requestSendStudentsGrade
    )
  }

  @Provides
  @Singleton
  fun provideRetrieveSectionStudents(
    requestInstructorSections: RequestInstructorSections,
    requestSectionStudents: RequestSectionStudents,
    requestSendStudentsGrade: RequestSendStudentsGrade
  ): RetrieveSectionStudents {
    return InstructorFacade(
      requestInstructorSections = requestInstructorSections,
      requestSectionStudents = requestSectionStudents,
      requestSendStudentsGrade = requestSendStudentsGrade
    )
  }

  @Provides
  @Singleton
  fun provideRetrieveStudentCourses(
    requestStudentCourses: RequestStudentCourses,
    requestStudentSemester: RequestStudentSemester,
    requestSendStudentTakes: RequestSendStudentTakes
  ): RetrieveStudentCourses {
    return StudentFacade(requestStudentCourses, requestStudentSemester, requestSendStudentTakes)
  }

  @Provides
  @Singleton
  fun provideRetrieveStudentSemester(
    requestStudentCourses: RequestStudentCourses,
    requestStudentSemester: RequestStudentSemester,
    requestSendStudentTakes: RequestSendStudentTakes
  ): RetrieveStudentSemester {
    return StudentFacade(requestStudentCourses, requestStudentSemester, requestSendStudentTakes)
  }


  @Provides
  @Singleton
  fun provideSubmitStudentTakes(
    requestStudentCourses: RequestStudentCourses,
    requestStudentSemester: RequestStudentSemester,
    requestSendStudentTakes: RequestSendStudentTakes
  ): SubmitStudentTakes {
    return StudentFacade(requestStudentCourses, requestStudentSemester, requestSendStudentTakes)
  }

  @Provides
  @Singleton
  fun provideRequestStudentCourses(studentDataSource: StudentDataSource): RequestStudentCourses {
    return StudentDataSourceImpl(studentDataSource)
  }

  @Provides
  @Singleton
  fun provideRequestStudentSemester(studentDataSource: StudentDataSource): RequestStudentSemester {
    return StudentDataSourceImpl(studentDataSource)
  }

  @Provides
  @Singleton
  fun provideRequestRequestSendStudentTakes(studentDataSource: StudentDataSource): RequestSendStudentTakes {
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

  @Provides
  @Singleton
  fun provideRequestSendStudentsGrade(instructorDataSource: InstructorDataSource): RequestSendStudentsGrade {
    return InstructorDataSourceImpl(instructorDataSource)
  }
}