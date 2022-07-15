package com.example.to_do_list.Room

import androidx.room.*

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTask(task: Tasks)

    @Query("Select * from tasks")
    fun readAllData(): List<Tasks>

    @Query("SELECT * FROM tasks WHERE categoryId = :id")
    fun getTaskByCategoryId(id:Int):List<Tasks>

    @Query("DELETE  FROM tasks WHERE taskId = :id")
    fun deleteTaskById(id:Int)



    @Update//Update Category set categoryName = category.name where id = category.id
    fun updateTask(task:Tasks)


    @Delete
    fun deleteTask(task: Tasks)
}