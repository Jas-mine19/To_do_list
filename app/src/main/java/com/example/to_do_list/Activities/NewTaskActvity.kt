package com.example.to_do_list.Activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.to_do_list.Room.MyDB
import com.example.to_do_list.Room.Tasks
import com.example.to_do_list.databinding.ActivityNewTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewTaskActvity : AppCompatActivity() {
    private var binding_: ActivityNewTaskBinding? = null
    private val binding get() = binding_!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_ = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db4 = MyDB.getInstance(this)
        val taskDao = db4.taskDao()
        val categoryDao = db4.categoryDao()
        val spinner = binding.spinnerCategory



        lifecycleScope.launch(Dispatchers.IO) {
            val categoryList = categoryDao.readAllData()
            val arrayAdapter = ArrayAdapter(
                this@NewTaskActvity,
                android.R.layout.simple_spinner_dropdown_item,
                categoryList.map { it.categoryName }
            )
            withContext(Dispatchers.Main) {
                spinner.adapter = arrayAdapter
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        Toast.makeText(this@NewTaskActvity, "selected", Toast.LENGTH_LONG).show()
                        binding.saveTask.setOnClickListener {
                            val task = Tasks(
                                0,
                                binding.taskName.text.toString(),
                                false,
                                categoryList[p2].categoryId
                            )
                            lifecycleScope.launch(Dispatchers.IO) {
                                taskDao.addTask(task)
                                finish()
                            }
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        Toast.makeText(this@NewTaskActvity, "is empty", Toast.LENGTH_LONG).show()
                    }


                }
            }

        }


    }
}