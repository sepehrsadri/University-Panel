package com.sadri.universitypanel.domain.login.core.model

import com.google.gson.annotations.SerializedName

data class AuthenticateRequest(
  @SerializedName("number")
  val number: String,
  @SerializedName("password")
  val password: String
)