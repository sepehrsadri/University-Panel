package com.sadri.universitypanel.domain.login.core.model

import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
  @SerializedName("token")
  val token: String,
  @SerializedName("name")
  val name: String
)