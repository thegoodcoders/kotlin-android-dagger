package com.androidavanzado.popcorn.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidavanzado.popcorn.api.response.Person

class SharedViewModel: ViewModel() {

    var personSelected: MutableLiveData<Person> = MutableLiveData()

    fun selectPerson(p: Person) {
        personSelected.value = p
    }

}