package com.example.to_do_list.Room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [Category::class, Tasks::class], version = 1, exportSchema = false)
abstract class MyDB : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: MyDB? = null

        fun getInstance(context: Context): MyDB {
            if (INSTANCE != null) {
                return INSTANCE!!
            }
            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MyDB::class.java,
                    "my_db").build()
                return INSTANCE!!
            }
        }

    }

}