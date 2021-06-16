package com.sadri.universitypanel.domain.instructor.grade.core.model

import com.google.gson.annotations.SerializedName
import com.sadri.universitypanel.domain.instructor.home.core.model.SectionStudentResponse

data class StudentGradeRequest(
  @SerializedName("grades")
  val grades: List<GradeRequest>
) {
  companion object {
    fun mapToStudentGradeRequest(
      sectionId: Int,
      list: List<SectionStudentResponse>
    ): StudentGradeRequest {
      return StudentGradeRequest(
        list.map {
          GradeRequest(
            sectionId = sectionId,
            studentId = it.id,
            grade = it.grade
          )
        }
      )
    }
  }
}

data class GradeRequest(
  @SerializedName("sectionId")
  val sectionId: Int,
  @SerializedName("studentId")
  val studentId: Int,
  @SerializedName("grade")
  val grade: String?
)