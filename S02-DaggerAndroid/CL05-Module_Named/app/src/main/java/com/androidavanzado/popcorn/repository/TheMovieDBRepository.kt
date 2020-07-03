package com.androidavanzado.popcorn.repository

import com.androidavanzado.popcorn.api.TheMovieDBService
import com.androidavanzado.popcorn.api.response.APIError
import com.androidavanzado.popcorn.common.MyApp
import org.json.JSONObject
import retrofit2.Response

class TheMovieDBRepository {
    private val theMovieDBService = MyApp.networkContainer.theMovieDBService

    suspend fun getPopularMovies() = theMovieDBService.getPopularMovies()

    suspend fun getPopularPeople() = theMovieDBService.getPopularPeople()

    suspend fun getPersonDetail(idPerson: Int) = theMovieDBService.getPersonDetail(idPerson)

    fun parseError(response: Response<*>): APIError {
        val jsonObject = JSONObject(response.errorBody()!!.string())
        return APIError(jsonObject.getInt("status_code"), jsonObject.getString("status_message"))
    }

}