package com.example.to_do_list.Activities

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.to_do_list.Room.MyDB
import com.example.to_do_list.Room.Tasks


import com.example.to_do_list.databinding.ActivityUpdateBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateTaskActivity : AppCompatActivity() {
    private var binding_: ActivityUpdateBinding? = null
    private val binding get() = binding_!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_ = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myDb = MyDB.getInstance(this)
        val taskDao = myDb.taskDao()
        val categoryDao = myDb.categoryDao()


        val bundle: Bundle? = intent.extras
        val taskName = bundle?.getString("NAME")
        val categoryId = bundle?.getString("CATEGORY")
        val taskId = bundle?.getInt("TASKID") ?: -1

        binding.taskName.setText(taskName)
        binding.spinnerCategory.setSelection(0)



        lifecycleScope.launch(Dispatchers.IO) {
            val categoryList = categoryDao.readAllData()
            val arrayAdapter = ArrayAdapter(
                this@UpdateTaskActivity,
                R.layout.simple_spinner_dropdown_item,
                categoryList.map { it.categoryName }
            )
            withContext(Dispatchers.Main) {
                binding.spinnerCategory.adapter = arrayAdapter
                binding.spinnerCategory.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            Toast.makeText(this@UpdateTaskActivity, "selected", Toast.LENGTH_LONG)
                                .show()
                            binding.updateButton.setOnClickListener {
                                val task = Tasks(
                                    taskId,
                                    binding.taskName.text.toString(),
                                    false,
                                    categoryList[p2].categoryId
                                )
                                lifecycleScope.launch(Dispatchers.IO) {
                                    taskDao.updateTask(task)
                                    finish()
                                }
                            }
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            Toast.makeText(this@UpdateTaskActivity, "is empty", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            }
        }

        binding.deleteButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {

                taskDao.deleteTaskById(taskId)
                withContext(Dispatchers.Main) {
                    finish()
                }
            }

        }


        binding.backButton.setOnClickListener {
            finish()
        }

    }
}