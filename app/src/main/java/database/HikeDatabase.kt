package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rebecca.hikingjournal.Hike

@Database(entities = [ Hike::class ], version=1)
@TypeConverters(HikeTypeConverters::class)
abstract class HikeDatabase : RoomDatabase() {
    abstract fun hikeDao(): HikeDao

}