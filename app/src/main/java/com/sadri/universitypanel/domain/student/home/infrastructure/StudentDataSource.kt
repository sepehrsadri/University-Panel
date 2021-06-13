package com.sadri.universitypanel.domain.student.home.infrastructure

import com.sadri.universitypanel.domain.student.home.core.model.StudentCourseResponse
import retrofit2.Response
import retrofit2.http.GET

interface StudentDataSource {
  @GET("student/courses")
  suspend fun retrieveCourses(): Response<List<StudentCourseResponse>>
}