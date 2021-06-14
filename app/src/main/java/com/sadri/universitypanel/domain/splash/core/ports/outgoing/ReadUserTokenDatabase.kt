package com.sadri.universitypanel.domain.splash.core.ports.outgoing

import kotlinx.coroutines.flow.Flow

interface ReadUserTokenDatabase {
  suspend fun retrieveToken(): Flow<String>
}