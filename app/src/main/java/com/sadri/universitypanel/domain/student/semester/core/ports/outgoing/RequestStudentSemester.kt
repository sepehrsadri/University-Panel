package com.sadri.universitypanel.domain.student.semester.core.ports.outgoing

import com.sadri.universitypanel.domain.student.semester.core.model.StudentSemesterResponse
import com.sadri.universitypanel.infrastructure.utils.ApiResult

interface RequestStudentSemester {
  suspend fun retrieveSemester(): ApiResult<List<StudentSemesterResponse>>
}