package com.example.nasamarsrover.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import com.example.nasamarsrover.API_PAGE_SIZE
import com.example.nasamarsrover.data.remote.NasaMarsRoverApiHelper
import com.example.nasamarsrover.model.MarsRoverPhoto

class PostDataSource (private val apiHelper: NasaMarsRoverApiHelper,
                      private val roverName: String,
                      private val sol: Int,
                      private val camera: String?) : PagingSource<Int, MarsRoverPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarsRoverPhoto> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            Log.d("apiTag::Key",currentLoadingPageKey.toString())
            Log.d("apiTag::LoadSize",params.loadSize.toString())
            val response = apiHelper.getPhotos(roverName, sol, camera, currentLoadingPageKey)
            val photos = response.body()?.photos ?: emptyList()
            Log.d("apiTag::Response",photos.toString())
            Log.d("apiTag::ResponseSize",photos.size.toString())

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = photos,
                prevKey = prevKey,
                nextKey = if(photos.size < API_PAGE_SIZE ) null else currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            e.message?.let { errorMessage ->
                Log.d("pageError",errorMessage)
            }
            return LoadResult.Error(e)
        }
    }
}