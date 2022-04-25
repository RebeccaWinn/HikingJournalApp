package com.example.rebecca.hikingjournal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class HikeDetailViewModel() : ViewModel(){
    private val hikeRepository = HikeRepository.get()
    private val hikeIdLiveData = MutableLiveData<UUID>()

    var hikeLiveData: LiveData<Hike?> =
            Transformations.switchMap(hikeIdLiveData){ hikeId->
                hikeRepository.getHike(hikeId)
    }
    fun loadHike(hikeId:UUID){
        hikeIdLiveData.value= hikeId
    }
    fun saveHike( hike:Hike){
        hikeRepository.updateHike(hike)
    }
}