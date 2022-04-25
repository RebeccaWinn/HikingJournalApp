package com.example.rebecca.hikingjournal

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import database.HikeDatabase
import java.util.*
import java.util.concurrent.Executors


private const val DATABASE_NAME= "hike-database"

class HikeRepository private constructor(context: Context){
    private val database : HikeDatabase= Room.databaseBuilder(
        context.applicationContext,
        HikeDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val hikeDao = database.hikeDao()
    private val executor =  Executors.newSingleThreadExecutor()

    fun getHikes(): LiveData<List<Hike>> = hikeDao.getHikes()

    fun getHike( id: UUID): LiveData<Hike?> = hikeDao.getHike(id)
    fun updateHike(hike:Hike){
        executor.execute{
            hikeDao.updateHike(hike)
        }
    }
    fun addHike(hike:Hike){
        executor.execute{
            hikeDao.addHike(hike)
        }
    }
    companion object{
        private var INSTANCE:HikeRepository? = null

        fun initialize(context:Context){
            if(INSTANCE == null){
                INSTANCE = HikeRepository(context)
            }
        }
        fun get(): HikeRepository{
            return INSTANCE?:
            throw IllegalStateException("HikeRepository must be initialized")
        }
    }
}