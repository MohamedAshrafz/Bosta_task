package com.example.bostatask_1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface AppApiInterface {
    @GET("users")
    suspend fun getUsers(): List<UserProperty>

    @GET("albums")
    suspend fun getAlbumsForUserId(@Query("userId") Id: String): List<AlbumProperty>
}

object Network {
    val NetworkServices: AppApiInterface by lazy {
        retrofit.create(AppApiInterface::class.java)
    }
}