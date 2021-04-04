package com.example.nasamarsrover.data.remote

import com.example.nasamarsrover.API_KEY
import com.example.nasamarsrover.model.MarsRoverResponse
import com.example.nasamarsrover.model.RoverModelWrapper
import retrofit2.Response
import javax.inject.Inject

class NasaMarsRoverApiHelperImpl @Inject constructor(val nasaMarsRoverService: NasaMarsRoverService) :
    NasaMarsRoverApiHelper {

    override suspend fun getPhotos(
        roverName: String,
        sol: Int,
        camera: String?,
        pageNumber: Int
    ): Response<MarsRoverResponse> =
        nasaMarsRoverService.getPhotos(roverName, sol, camera, API_KEY, pageNumber)

    override suspend fun getRoverInfo(roverName: String): Response<RoverModelWrapper> =
        nasaMarsRoverService.getRoverInfo(roverName, API_KEY)

}