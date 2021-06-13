package com.sadri.universitypanel.domain.instructor.home.infrastructure

import com.sadri.universitypanel.domain.instructor.home.core.model.InstructorSectionResponse
import retrofit2.Response
import retrofit2.http.GET

interface InstructorDataSource {
  @GET("instructor/sections")
  suspend fun retrieveSections(): Response<List<InstructorSectionResponse>>
}