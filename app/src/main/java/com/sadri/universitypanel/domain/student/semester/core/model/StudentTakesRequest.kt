package com.sadri.universitypanel.domain.student.semester.core.model

import com.google.gson.annotations.SerializedName

data class StudentTakesRequest(
  @SerializedName("takes")
  val grades: List<Int>
) {
  companion object {
    fun mapToStudentTakesRequest(
      list: List<StudentSemesterResponse>
    ): StudentTakesRequest {
      return StudentTakesRequest(
        list
          .filter { it.taken }
          .map { it.id }
      )
    }
  }
}