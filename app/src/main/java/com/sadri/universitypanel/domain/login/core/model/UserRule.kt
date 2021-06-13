package com.sadri.universitypanel.domain.login.core.model

enum class UserRule {
  STUDENT, INSTRUCTOR;

  fun getKey(): String {
    return when (this) {
      STUDENT -> "student"
      INSTRUCTOR -> "instructor"
    }
  }

  companion object {
    fun getRule(value: String?): UserRule {
      return when (value) {
        "student" -> STUDENT
        "instructor" -> INSTRUCTOR
        else -> throw RuntimeException("unspecified rule")
      }
    }
  }
}