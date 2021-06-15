package com.sadri.universitypanel.domain.student.semester.core.ports.outgoing

import com.sadri.universitypanel.domain.student.semester.core.model.StudentTakesRequest
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface RequestSendStudentTakes {
  suspend fun send(request: StudentTakesRequest): ApiResult<Void>
}