package com.example.nasamarsrover.data.remote

import com.example.nasamarsrover.model.MarsRoverResponse
import com.example.nasamarsrover.model.RoverModelWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaMarsRoverService {

    @GET("rovers/{roverName}/photos")
    suspend fun getPhotos(
        @Path(value = "roverName") roverName: String,
        @Query("sol") sol: Int,
        @Query("camera") camera: String?,
        @Query("api_key") type: String,
        @Query("page") pageNumber: Int
    ): Response<MarsRoverResponse>

    @GET(value = "rovers/{roverName}")
    suspend fun getRoverInfo(
        @Path(value="roverName") roverName: String,
        @Query(value ="api_key") apiKey: String
    ): Response<RoverModelWrapper>
}