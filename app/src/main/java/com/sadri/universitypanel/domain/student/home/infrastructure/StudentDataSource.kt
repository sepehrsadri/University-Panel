package com.sadri.universitypanel.domain.student.home.infrastructure

import com.sadri.universitypanel.domain.student.home.core.model.StudentCourseResponse
import com.sadri.universitypanel.domain.student.semester.core.model.StudentSemesterResponse
import com.sadri.universitypanel.domain.student.semester.core.model.StudentTakesRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface StudentDataSource {
  @GET("student/courses")
  suspend fun retrieveCourses(): Response<List<StudentCourseResponse>>

  @GET("student/take/courses")
  suspend fun retrieveSemester(): Response<List<StudentSemesterResponse>>

  @POST("student/take")
  suspend fun sendStudentTakes(
    @Body request: StudentTakesRequest
  ): Response<Void>
}