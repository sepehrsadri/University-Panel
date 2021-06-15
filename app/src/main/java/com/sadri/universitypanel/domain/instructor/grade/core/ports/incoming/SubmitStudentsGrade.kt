package com.sadri.universitypanel.domain.instructor.grade.core.ports.incoming

import com.sadri.universitypanel.domain.instructor.grade.core.model.StudentGradeRequest
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface SubmitStudentsGrade {
  suspend fun submit(request: StudentGradeRequest): ApiResult<Void>
}