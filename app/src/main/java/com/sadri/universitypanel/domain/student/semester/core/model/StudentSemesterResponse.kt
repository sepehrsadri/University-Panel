package com.sadri.universitypanel.domain.student.semester.core.model

import com.google.gson.annotations.SerializedName

data class StudentSemesterResponse(
  @SerializedName("id")
  val id: Int,
  @SerializedName("title")
  val title: String,
  @SerializedName("semester")
  val semester: String,
  @SerializedName("credits")
  val credits: Int,
  @SerializedName("instructors")
  val instructors: String,
  @Transient
  val taken: Boolean = false
)
