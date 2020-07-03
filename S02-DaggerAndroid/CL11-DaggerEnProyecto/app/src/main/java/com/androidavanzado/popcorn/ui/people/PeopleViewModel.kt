package com.androidavanzado.popcorn.ui.people

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidavanzado.popcorn.api.response.PopularPeopleResponse
import com.androidavanzado.popcorn.common.Resource
import com.androidavanzado.popcorn.repository.TheMovieDBRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class PeopleViewModel @Inject constructor(
    private val theMovieDBRepository: TheMovieDBRepository
): ViewModel() {

    var popularPeople: MutableLiveData<Resource<PopularPeopleResponse>> = MutableLiveData()

    init {
        getPopularPeople()
        Log.i("MOVIES", "theMovieDBRepository en PeopleViewModel: $theMovieDBRepository")
    }

    fun getPopularPeople() = viewModelScope.launch {
        popularPeople.value = Resource.Loading()
        delay(1000)
        val response = theMovieDBRepository.getPopularPeople()
        popularPeople.value = handlePopularPeopleResponse(response)
    }

    private fun handlePopularPeopleResponse(response: Response<PopularPeopleResponse>) : Resource<PopularPeopleResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}