package com.example.autoschool11.core.domain.repositories
 
interface UserStatsRepository {
    suspend fun saveStatsToServer()
    suspend fun loadStatsFromServer()
} 