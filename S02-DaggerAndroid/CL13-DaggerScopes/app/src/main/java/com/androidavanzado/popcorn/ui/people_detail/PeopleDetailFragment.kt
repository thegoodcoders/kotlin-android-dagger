package com.androidavanzado.popcorn.ui.people_detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import com.androidavanzado.popcorn.R
import com.androidavanzado.popcorn.common.MyApp
import com.androidavanzado.popcorn.common.Resource
import kotlinx.android.synthetic.main.fragment_people_detail.*
import javax.inject.Inject

class PeopleDetailFragment @Inject constructor(val peopleDetailViewModel: PeopleDetailViewModel): Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_people_detail, container, false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        showProgressBar()

        // Observer for personMovies
        peopleDetailViewModel.personInfoDetail.observe(viewLifecycleOwner, Observer { response ->
            Log.i("MOVIES", "sharedViewModel en PeopleDetailFragment: $peopleDetailViewModel")

            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { personDetail ->
                        text_view_bio.text = personDetail.biography
                        text_view_place_birth.text = personDetail.birthday
                        text_view_birthday.text = personDetail.place_of_birth
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

    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        text_view_bio.visibility = View.VISIBLE
        text_view_place_birth.visibility = View.VISIBLE
        text_view_birthday.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        text_view_bio.visibility = View.INVISIBLE
        text_view_place_birth.visibility = View.INVISIBLE
        text_view_birthday.visibility = View.INVISIBLE
    }

}
