package com.androidavanzado.popcorn.ui.people

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.androidavanzado.popcorn.R
import com.androidavanzado.popcorn.api.response.Person
import com.androidavanzado.popcorn.common.Constants
import com.androidavanzado.popcorn.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_people_list_item.view.*

class PeopleRecyclerViewAdapter(private val sharedViewModel: SharedViewModel
) : RecyclerView.Adapter<PeopleRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var people: List<Person> = ArrayList()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Person
            sharedViewModel.selectPerson(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_people_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = people[position]
        holder.tvName.text = item.name

        var avgFilms = 0.0
        for (film in item.known_for) {
            avgFilms += film.vote_average
        }

        avgFilms /= item.known_for.size

        holder.tvRate.text = String.format("%.1f", avgFilms)

        holder.ivPhoto.load(Constants.IMAGE_BASE_URL + item.profile_path) {
            crossfade(true)
            placeholder(R.drawable.ic_bg_circle_empty)
            transformations(CircleCropTransformation())
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = people.size

    fun setData(popularPeople: List<Person>?) {
        people = popularPeople!!
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvName: TextView = mView.text_view_name
        val tvRate: TextView = mView.text_view_rating
        val ivPhoto: ImageView = mView.image_view_photo
    }
}
