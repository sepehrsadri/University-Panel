package com.sadri.universitypanel.domain.student.home.core.model

import com.google.gson.annotations.SerializedName

data class StudentCourseResponse(
  @SerializedName("id")
  val id: String,
  @SerializedName("title")
  val title: String,
  @SerializedName("semester")
  val semester: String,
  @SerializedName("grade")
  val grade: String?,
  @SerializedName("credits")
  val credits: Int
)
