package com.example.nasamarsrover.data.remote

import com.example.nasamarsrover.model.MarsRoverResponse
import com.example.nasamarsrover.model.RoverModelWrapper
import retrofit2.Response

interface NasaMarsRoverApiHelper {

    suspend fun getPhotos(
        roverName: String,
        sol: Int?,
        earthDate: String?,
        camera: String?,
        pageNumber: Int
    ): Response<MarsRoverResponse>

    suspend fun getRoverInfo(
        roverName: String
    ): Response<RoverModelWrapper>
}