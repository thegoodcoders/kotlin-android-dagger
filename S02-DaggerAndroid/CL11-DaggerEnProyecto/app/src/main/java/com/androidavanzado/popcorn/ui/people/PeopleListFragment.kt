package com.androidavanzado.popcorn.ui.people

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.androidavanzado.popcorn.R
import com.androidavanzado.popcorn.common.Resource
import com.androidavanzado.popcorn.common.MyApp
import com.androidavanzado.popcorn.viewmodel.SharedViewModel
import javax.inject.Inject

class PeopleListFragment : Fragment() {

    lateinit var sharedViewModel: SharedViewModel
    @Inject
    lateinit var peopleViewModel: PeopleViewModel

    private var columnCount = 2
    private lateinit var paginationProgressBar: LottieAnimationView
    private lateinit var peopleAdapter: PeopleRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as MyApp).appComponent.inject(this)

        sharedViewModel = ViewModelProvider(activity!!).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_people_list, container, false)


        // Set information
        paginationProgressBar = view.findViewById(R.id.paginationProgressBar)

        // Observer for peopleAdapter
        peopleAdapter = PeopleRecyclerViewAdapter(sharedViewModel)

        // Set the adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)

        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = peopleAdapter
        }

        // Observer for popularMovies
        peopleViewModel.popularPeople.observe(viewLifecycleOwner, Observer { response ->

            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.results?.let { peopleResults ->
                        peopleAdapter.setData(peopleResults)
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
