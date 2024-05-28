package com.fatih.itunessmoviesearchapp.data.api

import com.fatih.itunessmoviesearchapp.data.dto.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("/search")
    suspend fun getViews(
        @Query("term") term : String,
        @Query("media") entity: String = "movie",
        @Query("limit") limit : Int = 20,
        @Query("offset") offset: Int = 0
    ) :Response
}