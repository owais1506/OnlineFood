package com.os.onlinefood.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.os.onlinefood.data.dao.MainDao
import com.os.onlinefood.data.model.DonutMaster
import com.os.onlinefood.data.model.ToppingMaster


@Database(
    entities = [DonutMaster::class,ToppingMaster::class],version = 3,exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {
    companion object {

        @Volatile
        var instance: MainDatabase? = null

        private val LOCK = Any()

        const val DATABASE_NAME = "DMAssignment.db"

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        fun buildDatabase(context: Context): MainDatabase {
            return Room.databaseBuilder(
                context,
                MainDatabase::class.java,
                DATABASE_NAME
            )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        }
    }

    abstract fun mainDao(): MainDao?

}