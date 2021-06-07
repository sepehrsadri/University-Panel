package com.sadri.universitypanel.domain.login.core.model

enum class UserRule {
  STUDENT, MASTER;

  fun getKey(): String {
    return when (this) {
      STUDENT -> "student"
      MASTER -> "master"
    }
  }

  companion object {
    fun getRule(value: String?): UserRule {
      return when (value) {
        "student" -> STUDENT
        "master" -> MASTER
        else -> throw RuntimeException("unspecified rule")
      }
    }
  }
}