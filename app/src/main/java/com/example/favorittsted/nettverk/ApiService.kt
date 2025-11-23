package com.example.favorittsted.nettverk

import com.example.favorittsted.data.FavorittSted
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val BASE_URL = "https://dave3600.cs.oslomet.no/~demar4981/" // URL til API

private val moshi = Moshi.Builder() // Deserialiserer JSON-objekter fra API
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder() // Kaller på API-URL
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("jsonout.php") // Henter JSON-objekter
    suspend fun getFavoritePlace(): List<FavorittSted>

    @GET("jsonin.php") // Legger inn JSON-objekter
    suspend fun putPlace(
        @Query("Name") name: String,
        @Query("Description") description: String,
        @Query("Address") address: String,
        @Query("Latitude") latitude: String,
        @Query("Longitude") longitude: String
    ) : String

    @GET("jsonupdate.php")
    suspend fun updatePlace( // Oppdaterer JSON-objekter
        @Query("id") id: Int,
        @Query("Name") name: String,
        @Query("Description") description: String,
        @Query("Address") address: String,
        @Query("Latitude") latitude: String,
        @Query("Longitude") longitude: String
    ) : String

    @GET("jsondelete.php")
    suspend fun deletePlace( // Sletter et JSON-objekt
        @Query("id") id: Int
    ) : String


}

object Api {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}