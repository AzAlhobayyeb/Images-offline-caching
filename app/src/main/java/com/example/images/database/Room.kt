package com.example.images.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ImagesDao{
    @Query("select * from DatabaseImages")
    fun getImages(): LiveData<List<DatabaseImages>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( images: List<DatabaseImages>)
}

@Database(entities = [DatabaseImages::class], version = 1)
abstract class ImagesDatabase: RoomDatabase() {
    abstract val imagesDao: ImagesDao
}

private lateinit var INSTANCE: ImagesDatabase

fun getDatabase(context: Context): ImagesDatabase {
    synchronized(ImagesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ImagesDatabase::class.java,
                "images").build()
        }
    }
    return INSTANCE
}