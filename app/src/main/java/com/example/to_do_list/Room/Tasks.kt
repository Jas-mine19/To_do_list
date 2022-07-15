package com.example.to_do_list.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tasks(
    @PrimaryKey(autoGenerate = true)
    val taskId: Int,
    val taskName: String,
    val isSelected: Boolean,
    val categoryId:Int

)