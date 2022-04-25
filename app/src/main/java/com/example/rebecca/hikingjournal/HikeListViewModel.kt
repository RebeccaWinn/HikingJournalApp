package com.example.rebecca.hikingjournal

import androidx.lifecycle.ViewModel

class HikeListViewModel: ViewModel(){
    private val hikeRepository = HikeRepository.get()
    val hikeListLiveData = hikeRepository.getHikes()
    fun addHike(hike: Hike) {
        hikeRepository.addHike(hike)
    }
}

