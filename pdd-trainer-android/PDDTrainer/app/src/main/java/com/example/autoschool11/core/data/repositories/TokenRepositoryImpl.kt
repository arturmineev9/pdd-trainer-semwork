package com.example.autoschool11.core.data.repositories

import com.example.autoschool11.core.data.local.TokenStorage
import com.example.autoschool11.core.domain.repositories.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(
    private val tokenStorage: TokenStorage
) : TokenRepository {
    override fun saveToken(token: String) {
        tokenStorage.saveToken(token)
    }

    override fun getToken(): String? {
        return tokenStorage.getToken()
    }

    override fun clearToken() {
        tokenStorage.clearToken()
    }
}
