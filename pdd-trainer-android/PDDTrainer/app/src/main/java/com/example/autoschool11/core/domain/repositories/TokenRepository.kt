package com.example.autoschool11.core.domain.repositories
 
interface TokenRepository {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
} 