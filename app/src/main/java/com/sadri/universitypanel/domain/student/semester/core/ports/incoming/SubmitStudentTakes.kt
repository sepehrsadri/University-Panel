package com.sadri.universitypanel.domain.student.semester.core.ports.incoming

import com.sadri.universitypanel.domain.student.semester.core.model.StudentTakesRequest
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface SubmitStudentTakes {
  suspend fun submit(request: StudentTakesRequest): ApiResult<Void>
}