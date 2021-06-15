package com.sadri.universitypanel.domain.student.semester.core.ports.incoming

import com.sadri.universitypanel.domain.student.semester.core.model.StudentSemesterResponse
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface RetrieveStudentSemester {
  suspend fun retrieveSemester(): ApiResult<List<StudentSemesterResponse>>
}