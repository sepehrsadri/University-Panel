package com.sadri.universitypanel.domain.student.home.infrastructure

import com.sadri.universitypanel.domain.student.home.core.model.CourseResponse
import retrofit2.Response
import retrofit2.http.GET

interface StudentDataSource {
  @GET("student/courses")
  suspend fun getCourses(): Response<List<CourseResponse>>
}