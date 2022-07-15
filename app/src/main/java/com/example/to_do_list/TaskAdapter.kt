package com.example.to_do_list

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.Room.Tasks
import com.example.to_do_list.databinding.TaskItemRv2Binding

class TaskAdapter : ListAdapter<Tasks, TaskAdapter.ItemViewHolder>(
    TasksDiffUtil()
) {


    var onItemClick: ((Tasks) -> Unit)? = null
    var onCheckboxClick: ((Tasks) -> Unit)? = null

    private class TasksDiffUtil : DiffUtil.ItemCallback<Tasks>() {
        override fun areItemsTheSame(
            oldItem: Tasks,
            newItem: Tasks
        ): Boolean {
            return oldItem.taskId == newItem.taskId
        }

        override fun areContentsTheSame(
            oldItem: Tasks,
            newItem: Tasks
        ): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ItemViewHolder =
        ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.task_item_rv2, parent, false)
        )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.buttonRv2.text = getItem(position).taskName
        holder.binding.checkbox.isChecked = getItem(position).isSelected
    }


    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = TaskItemRv2Binding.bind(view)


        init {

            binding.checkbox.setOnCheckedChangeListener { _, b ->
                onCheckboxClick?.invoke(
                    Tasks(
                        getItem(adapterPosition).taskId,
                        getItem(adapterPosition).taskName,
                        b,
                        getItem(adapterPosition).categoryId
                    )
                )
            }

            view.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition))

            }
        }
    }

}