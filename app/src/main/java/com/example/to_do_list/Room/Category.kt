package com.example.to_do_list.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category (
    @PrimaryKey( autoGenerate = true)
    val categoryId:Int,
    val categoryName:String,
    val isSelected:Boolean
        )

