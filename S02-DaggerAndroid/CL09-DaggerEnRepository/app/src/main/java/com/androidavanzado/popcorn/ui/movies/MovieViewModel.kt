package com.androidavanzado.popcorn.ui.movies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidavanzado.popcorn.repository.TheMovieDBRepository
import com.androidavanzado.popcorn.api.response.PopularMoviesResponse
import com.androidavanzado.popcorn.common.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class MovieViewModel @Inject constructor(
        private val theMovieDBRepository: TheMovieDBRepository
    ): ViewModel() {

    var popularMovies: MutableLiveData<Resource<PopularMoviesResponse>> = MutableLiveData()

    init {
        getPopularMovies()
    }

    fun getPopularMovies() = viewModelScope.launch {
        popularMovies.value = Resource.Loading()
        delay(3000)
        val response = theMovieDBRepository.getPopularMovies()
        popularMovies.value = handlePopularMoviesResponse(response)
    }

    private fun handlePopularMoviesResponse(response: Response<PopularMoviesResponse>) : Resource<PopularMoviesResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}