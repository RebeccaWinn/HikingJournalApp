package com.example.rebecca.hikingjournal

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Hike(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var name: String="",
                var location:String="",
                var journal:String="",
                var difficulty:String="",
                var distance: String="",
                var isHiked:Boolean= false
)