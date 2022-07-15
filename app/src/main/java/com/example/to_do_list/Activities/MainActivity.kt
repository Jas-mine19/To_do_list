package com.example.to_do_list.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import com.example.to_do_list.CategoryAdapter
import com.example.to_do_list.Room.*
import com.example.to_do_list.TaskAdapter
import com.example.to_do_list.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {


    private var binding_: ActivityMainBinding? = null
    private val binding get() = binding_!!


    private val categoryAdapter = CategoryAdapter()
    private val taskAdapter = TaskAdapter()
    private lateinit var viewModel: MainViewModel

    lateinit var db1: MyDB
    lateinit var categoryDao: CategoryDao
    lateinit var tasksDao: TaskDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_ = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.categoryRv.adapter = categoryAdapter
        binding.taskRv.adapter = taskAdapter
        db1 = MyDB.getInstance(this)
        categoryDao = db1.categoryDao()
        tasksDao = db1.taskDao()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupCategoriesList()
        setupListeners()
        setupList()




        binding.addButtonTask.setOnClickListener {
            Intent(this, NewTaskActvity::class.java).also {
                startActivity(it)
            }
        }


        binding.addButtonCategory.setOnClickListener {
            Intent(this, NewCategoryActivity::class.java).also {
                startActivity(it)
            }
        }


    }

    override fun onResume() {
        super.onResume()
        setupList()

    }


    private fun setupListeners() {
        categoryAdapter.onItemClick = {
            setupCategoriesList(it)
        }

        taskAdapter.onItemClick = {
            val intent = Intent(this@MainActivity, UpdateTaskActivity::class.java)
            intent.putExtra("NAME", it.taskName)
            intent.putExtra("CATEGORY", it.categoryId)
            intent.putExtra("TASKID", it.taskId)
            startActivity(intent)
        }

        taskAdapter.onCheckboxClick = {
            lifecycleScope.launch(Dispatchers.IO) {
                tasksDao.updateTask(it)
            }
        }

    }


    private fun setupCategoriesList(selectedCategoryId: Int = 1) {
        lifecycleScope.launch(Dispatchers.IO) {
            val categoryList = viewModel.categoryList()
            withContext(Dispatchers.Main) {
                categoryAdapter.submitList(
                    categoryList
                )
                setupTask(selectedCategoryId)
            }
        }


    }

    private fun setupTask(id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            val taskList = viewModel.taskListById(id)
            withContext(Dispatchers.Main) {
                taskAdapter.submitList(
                    taskList
                )
            }
        }
    }

    private fun setupList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val listOfCategory = categoryDao.readAllData()
            val listOfTask = tasksDao.readAllData()
            withContext(Dispatchers.Main) {
                categoryAdapter.submitList(listOfCategory)
                taskAdapter.submitList(listOfTask)
            }
        }
    }
}








