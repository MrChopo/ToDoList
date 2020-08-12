package com.example.todolist

import Data.TaskDataBase
import Model.Task
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_task_list.*
import kotlinx.coroutines.*

class TaskListActivity : AppCompatActivity() {

    lateinit var db: TaskDataBase
    lateinit var taskList: MutableList<Task>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        db = Room.databaseBuilder(
        applicationContext,
        TaskDataBase::class.java, "taskDB").allowMainThreadQueries().build()

        taskList= db.taskDao().getAll()


        val recyclerView: RecyclerView = findViewById(R.id.taskRecyclerView)
        val taskAdapter = TaskAdapter(taskList,this)

        recyclerView.adapter = taskAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        addButton.setOnClickListener(View.OnClickListener {
            addAndEditTask(false, null, null)
        })


    }

    fun addAndEditTask(isUpdate: Boolean, task: Task?, position: Int?) {
        val layoutInflaterAndroid = LayoutInflater.from(applicationContext)
        val view: View = layoutInflaterAndroid.inflate(R.layout.add_task, null)
        val alertDialogBuilderUserInput = AlertDialog.Builder(this)
        alertDialogBuilderUserInput.setView(view)

        val newTaskDescription: EditText = view.findViewById(R.id.taskDescriptionEditText)
        //val newTaskSwitchCompat: SwitchCompat = view.findViewById(R.id.taskSwitchCompat)

        if (isUpdate && task != null){
            newTaskDescription.setText(task.description)
           // newTaskSwitchCompat.isChecked = task.isDone
        }

        alertDialogBuilderUserInput.setCancelable(false).setPositiveButton(
                if (isUpdate) "Update" else "Save") { dialogBox, id -> }.setNegativeButton(
                if (isUpdate) "Delete" else "Cancel") { dialogBox, id ->
                if (isUpdate) {
                   // deleteCar(car, position)
                } else {
                    dialogBox.cancel()
                }
            }
        val alertDialog = alertDialogBuilderUserInput.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setOnClickListener(View.OnClickListener {
                if (TextUtils.isEmpty(newTaskDescription.text.toString())) {
                    Toast.makeText(this, "Enter task", Toast.LENGTH_SHORT)
                        .show()
                    return@OnClickListener
                } else {
                    alertDialog.dismiss()
                }
                if (isUpdate && task != null) {

                } else {
                    createCar(
                        newTaskDescription.text.toString(),
                        false
                    )
                }
            })

    }

    fun createCar (description: String, isDone: Boolean){
        
        val id = db.taskDao().insertTask(Task(0, description, isDone))

        val task = db.taskDao().getTaskById(id)
        taskList.add(task)

    }


}