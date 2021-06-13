package com.sadri.universitypanel.domain.instructor.home.core.model

import com.google.gson.annotations.SerializedName

data class InstructorSectionResponse(
  @SerializedName("id")
  val id: Int,
  @SerializedName("title")
  val title: String,
  @SerializedName("semester")
  val semester: String,
  @SerializedName("credits")
  val credits: Int,
  @SerializedName("students")
  val students: Int
)