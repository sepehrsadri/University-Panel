package com.sadri.universitypanel.domain.login.core.model

sealed class UserRuleChipState(val userRule: UserRule) {
  object Student : UserRuleChipState(UserRule.STUDENT)
  object Master : UserRuleChipState(UserRule.MASTER)
}