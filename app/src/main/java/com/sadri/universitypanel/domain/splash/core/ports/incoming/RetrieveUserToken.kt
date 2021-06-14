package com.sadri.universitypanel.domain.splash.core.ports.incoming

import kotlinx.coroutines.flow.Flow

interface RetrieveUserToken {
  suspend fun retrieveToken(): Flow<String>
}