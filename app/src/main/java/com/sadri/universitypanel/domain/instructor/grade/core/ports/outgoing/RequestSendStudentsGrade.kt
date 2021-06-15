package com.sadri.universitypanel.domain.instructor.grade.core.ports.outgoing

import com.sadri.universitypanel.domain.instructor.grade.core.model.StudentGradeRequest
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface RequestSendStudentsGrade {
  suspend fun send(request: StudentGradeRequest): ApiResult<Void>
}