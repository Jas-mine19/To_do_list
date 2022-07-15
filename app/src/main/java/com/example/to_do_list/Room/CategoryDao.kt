package com.example.to_do_list.Room

import android.view.View
import androidx.room.*

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCategory(category: Category)

    @Query("SELECT * FROM category_table")
    fun readAllData(): List<Category>



    @Update//Update Category set categoryName = category.name where id = category.id
    fun updateCategory(category: Category)




    @Delete
    fun deleteCategory(category:Category)
}