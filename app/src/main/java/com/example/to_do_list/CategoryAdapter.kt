package com.example.to_do_list

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.Room.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryAdapter : ListAdapter<Category, CategoryAdapter.ButtonViewHolder>(
    FoodCategoryDiffUtil()
) {


    var onItemClick: ((Int) -> Unit)? = null

    private class FoodCategoryDiffUtil : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem.categoryId == newItem.categoryId
        }

        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.ButtonViewHolder =
        ButtonViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item_rv1, parent, false)
        )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: CategoryAdapter.ButtonViewHolder, position: Int) {
        holder.text.text = getItem(position).categoryName

        if (getItem(position).isSelected) {
            holder.cardButton.setBackgroundResource(R.color.white)
            holder.text.setTextColor(
                holder.itemView.resources.getColor(
                    R.color.black,

                    null
                )
            )
        } else {
            holder.cardButton.setBackgroundResource(R.drawable.button_unactive)
            holder.text.setTextColor(
                holder.itemView.resources.getColor(
                    R.color.white,
                    null
                )
            )

        }
    }


    inner class ButtonViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.button_rv1)
        val cardButton: CardView = view.findViewById(R.id.button_card)


        init {
            view.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition).categoryId)
            }
        }
    }
}