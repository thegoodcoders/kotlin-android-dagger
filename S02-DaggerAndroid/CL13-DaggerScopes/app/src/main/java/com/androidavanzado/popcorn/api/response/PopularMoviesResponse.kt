package com.androidavanzado.popcorn.api.response

import com.androidavanzado.popcorn.api.response.Movie

data class PopularMoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)