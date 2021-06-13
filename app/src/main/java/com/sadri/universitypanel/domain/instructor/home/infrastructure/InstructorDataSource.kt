package com.sadri.universitypanel.domain.instructor.home.infrastructure

import com.sadri.universitypanel.domain.instructor.home.core.model.InstructorSectionResponse
import com.sadri.universitypanel.domain.instructor.home.core.model.SectionStudentResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface InstructorDataSource {
  @GET("instructor/sections")
  suspend fun retrieveSections(): Response<List<InstructorSectionResponse>>

  @GET("instructor/students/{sectionId}")
  suspend fun retrieveStudents(
    @Path("sectionId") sectionId: Int
  ): Response<List<SectionStudentResponse>>
}