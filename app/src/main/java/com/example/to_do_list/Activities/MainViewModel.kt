package com.example.to_do_list.Activities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.to_do_list.Room.Category
import com.example.to_do_list.Room.MyDB
import com.example.to_do_list.Room.Tasks

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val db2 = MyDB.getInstance(application.applicationContext)
    val categoryDao = db2.categoryDao()
    val taskDao = db2.taskDao()

    suspend fun categoryList(): List<Category> {
        val categoryList = categoryDao.readAllData()
        return categoryList
    }

    suspend fun taskListById(id : Int): List<Tasks> {
        return taskDao.getTaskByCategoryId(id)
    }


    override fun onCleared() {
        super.onCleared()
    }
}

