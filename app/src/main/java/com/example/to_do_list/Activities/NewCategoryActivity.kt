package com.example.to_do_list.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.to_do_list.Room.Category
import com.example.to_do_list.Room.MyDB
import com.example.to_do_list.databinding.ActivityNewCategoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewCategoryActivity : AppCompatActivity() {
    private var binding_: ActivityNewCategoryBinding? = null
    private val binding get() = binding_!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_ = ActivityNewCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var db3 = MyDB.getInstance(this)
        val dao = db3.categoryDao()


        binding.saveCategory.setOnClickListener {
            val category = Category(0, binding.categoryName.text.toString(), false)
            lifecycleScope.launch(Dispatchers.IO) {
                dao.addCategory(category)
                finish()

            }
        }



    }
}
