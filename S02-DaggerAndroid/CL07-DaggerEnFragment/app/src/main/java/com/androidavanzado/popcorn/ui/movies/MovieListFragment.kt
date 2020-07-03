package com.androidavanzado.popcorn.ui.movies

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.androidavanzado.popcorn.R
import com.androidavanzado.popcorn.common.MyApp
import com.androidavanzado.popcorn.common.Resource
import com.androidavanzado.popcorn.viewmodel.SharedViewModel
import javax.inject.Inject

class MovieListFragment : Fragment() {
    @Inject lateinit var movieViewModel: MovieViewModel
    lateinit var sharedPref: SharedPreferences

    private lateinit var paginationProgressBar: LottieAnimationView
    private lateinit var movieAdapter: MovieRecyclerViewAdapter
    private var columnCount = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as MyApp).appComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)

        paginationProgressBar = view.findViewById(R.id.paginationProgressBar)
        movieAdapter = MovieRecyclerViewAdapter()

        // Set the adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)

        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = movieAdapter
        }

        // Observer for popularMovies
        movieViewModel.popularMovies.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.results?.let { moviesResults ->
                        movieAdapter.setData(moviesResults)
                        recyclerView.scheduleLayoutAnimation()
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("Error", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        return view
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {

        paginationProgressBar.visibility = View.VISIBLE
    }


}
