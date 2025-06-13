package com.example.autoschool11.core.data.remote.di

import android.net.Uri
import com.example.autoschool11.core.data.remote.adapters.UriTypeAdapter
import com.example.autoschool11.core.data.remote.api.AuthApi
import com.example.autoschool11.core.data.remote.api.SignRecognitionApi
import com.example.autoschool11.core.data.remote.api.UserStatsApi
import com.example.autoschool11.core.data.repositories.AuthRepositoryImpl
import com.example.autoschool11.core.domain.repositories.AuthRepository
import com.example.autoschool11.core.domain.repositories.TokenRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl(): String = "http://10.0.2.2:5268/"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenRepository: TokenRepository
    ): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val token = tokenRepository.getToken()
            val request = chain.request().newBuilder()
            if (token != null) {
                request.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(request.build())
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideSignRecognitionApi(retrofit: Retrofit): SignRecognitionApi =
        retrofit.create(SignRecognitionApi::class.java)

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Uri::class.java, UriTypeAdapter())
            .create()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)


    @Provides
    @Singleton
    fun provideUserStatsApi(retrofit: Retrofit): UserStatsApi =
        retrofit.create(UserStatsApi::class.java)

}
