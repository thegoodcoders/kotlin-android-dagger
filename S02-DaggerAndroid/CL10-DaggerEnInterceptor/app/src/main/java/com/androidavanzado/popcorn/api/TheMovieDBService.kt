package com.androidavanzado.popcorn.api

import com.androidavanzado.popcorn.api.response.PersonDetail
import com.androidavanzado.popcorn.api.response.PopularMoviesResponse
import com.androidavanzado.popcorn.api.response.PopularPeopleResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDBService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<PopularMoviesResponse>

    @GET("person/popular")
    suspend fun getPopularPeople(): Response<PopularPeopleResponse>

    @GET("person/{person_id}")
    suspend fun getPersonDetail(@Path("person_id") idPerson: Int): Response<PersonDetail>
}