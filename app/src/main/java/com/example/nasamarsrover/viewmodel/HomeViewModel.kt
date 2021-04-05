package com.example.nasamarsrover.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.example.nasamarsrover.API_PAGE_SIZE
import com.example.nasamarsrover.data.remote.NasaMarsRoverApiHelper
import com.example.nasamarsrover.data.datasource.PostDataSource
import com.example.nasamarsrover.data.enums.RoverType
import com.example.nasamarsrover.data.local.MarsRoverPreferenceRepository
import com.example.nasamarsrover.model.CameraModel
import com.example.nasamarsrover.model.MarsRoverPhoto
import com.example.nasamarsrover.model.RoverModel
import com.example.nasamarsrover.model.RoverModelWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiHelper: NasaMarsRoverApiHelper,
    private val marsRoverPreferenceRepository: MarsRoverPreferenceRepository
) : ViewModel() {

    val cameraLiveData = MutableLiveData<String>()
    val currentRover = MutableLiveData<String>()
    val cameraFilterList = MediatorLiveData<List<CameraModel>>().apply {
        addSource(currentRover){ rover ->
            when(rover){
                RoverType.Spirit.roverName -> {
                    spiritInfo.value?.let {
                        value = it.cameras
                    }
                }
                RoverType.Opportunity.roverName -> {
                    opportunityInfo.value?.let {
                        value = it.cameras
                    }
                }
                else -> {
                    curiosityInfo.value?.let {
                        value = it.cameras
                    }
                }
            }
        }
    }

    val curiosityInfo = MediatorLiveData<RoverModel>().apply {
        addSource(Transformations.map(marsRoverPreferenceRepository.getRoverInfoLiveData(RoverType.Curiosity.roverName)) {
            it == null
        }){ remoteRequired ->
            if(remoteRequired){
                viewModelScope.launch {
                    getRoverInfoFromRemote(RoverType.Curiosity.roverName).saveRoverModelToPreference()
                }
            } else{
                postValue(getRoverInfoFromLocalRepository(RoverType.Curiosity.roverName))
            }
        }
    }

    val opportunityInfo = MediatorLiveData<RoverModel>().apply {
        addSource(Transformations.map(marsRoverPreferenceRepository.getRoverInfoLiveData(RoverType.Opportunity.roverName)) {
            it == null
        }){ remoteRequired ->
            if(remoteRequired){
                viewModelScope.launch {
                    getRoverInfoFromRemote(RoverType.Opportunity.roverName).saveRoverModelToPreference()
                }
            } else{
                postValue(getRoverInfoFromLocalRepository(RoverType.Opportunity.roverName))
            }
        }
    }

    val spiritInfo = MediatorLiveData<RoverModel>().apply {
        addSource(Transformations.map(marsRoverPreferenceRepository.getRoverInfoLiveData(RoverType.Spirit.roverName)) {
            it == null
        }){ remoteRequired ->
            if(remoteRequired){
                viewModelScope.launch {
                    getRoverInfoFromRemote(RoverType.Spirit.roverName).saveRoverModelToPreference()
                }
            } else{
                postValue(getRoverInfoFromLocalRepository(RoverType.Spirit.roverName))
            }
        }
    }

    private fun getRoverInfoFromLocalRepository(roverName: String): RoverModel? =
        marsRoverPreferenceRepository.getRoverInfo(roverName)

    private fun Response<RoverModelWrapper>.saveRoverModelToPreference() {
        if (this.isSuccessful) {
            val roverModel = this.body()?.rover
            roverModel?.let {
                marsRoverPreferenceRepository.saveRoverInfo(it)
            }
        }
    }

    private suspend fun getRoverInfoFromRemote(roverName: String): Response<RoverModelWrapper> =
        apiHelper.getRoverInfo(roverName)

    fun getPhotosFromRemote(roverName: String): Flow<PagingData<MarsRoverPhoto>> {
        return Pager(PagingConfig(pageSize = API_PAGE_SIZE)) {
            PostDataSource(apiHelper, roverName,1000, cameraLiveData.value)

        }.flow.cachedIn(viewModelScope)
    }
}