package com.sadri.universitypanel.domain.instructor.home.core.model

import com.google.gson.annotations.SerializedName

data class SectionStudentResponse(
  @SerializedName("id")
  val id: Int,
  @SerializedName("name")
  val name: String,
  @SerializedName("grade")
  val grade: String
)