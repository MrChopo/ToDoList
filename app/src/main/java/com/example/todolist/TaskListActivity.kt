package com.example.todolist

import Data.TaskDataBase
import Model.Task
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_task_list.*
import java.text.SimpleDateFormat
import java.util.*

class TaskListActivity : AppCompatActivity() {

    lateinit var db: TaskDataBase
    lateinit var taskList: MutableList<Task>
    lateinit var taskAdapter: TaskAdapter

    var day: String = ""

    val pattern = "yy.MM.dd"
    val simpleDateFormat = SimpleDateFormat(pattern)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        if(intent != null){
            day = intent.getStringExtra("day").toString()
        }else{
            day = simpleDateFormat.format(Date())
        }
        setTitle(day)


        db = Room.databaseBuilder(
        applicationContext,
        TaskDataBase::class.java, "taskDB").allowMainThreadQueries().build()

        Log.v("DayString", day)


        taskList = db.taskDao().getAllOnDay(day)



        val recyclerView: RecyclerView = findViewById(R.id.taskRecyclerView)
        taskAdapter = TaskAdapter(taskList,this)

        recyclerView.adapter = taskAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        addButton.setOnClickListener {
            addAndEditTask(false, null, null)
        }


    }


    fun addAndEditTask(isUpdate: Boolean, task: Task?, position: Int?) {
        val layoutInflaterAndroid = LayoutInflater.from(applicationContext)
        val view: View = layoutInflaterAndroid.inflate(R.layout.add_task, null)
        val alertDialogBuilderUserInput = AlertDialog.Builder(this)
        alertDialogBuilderUserInput.setView(view)

        val newTaskDescription: EditText = view.findViewById(R.id.taskDescriptionEditText)
        //val newTaskSwitchCompat: SwitchCompat = view.findViewById(R.id.taskSwitchCompat)

        if (isUpdate && task != null){
            newTaskDescription.setText(task.description, TextView.BufferType.EDITABLE)
           // newTaskSwitchCompat.isChecked = task.isDone
        }

        alertDialogBuilderUserInput.setCancelable(false).setPositiveButton(
                if (isUpdate) "Update" else "Save") { dialogBox, id -> }.setNegativeButton(
                if (isUpdate) "Delete" else "Cancel") { dialogBox, id ->
                if (isUpdate) {
                    if (task != null && position != null) {
                        deleteTask(task, position)
                    }
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
                    upgradeDescriptionTask(position!!, newTaskDescription.text.toString())
                } else {
                    createTask(
                        newTaskDescription.text.toString(),
                        false
                    )
                }
            })

    }

    fun createTask (description: String, isDone: Boolean){
        
        val id = db.taskDao().insertTask(Task(0, description, isDone, day))

        val task = db.taskDao().getTaskById(id)
        taskList.add(task)

    }

    fun upgradeCheckTask (position: Int, isDone: Boolean){

        val task = taskList[position]
        task.isDone = isDone
        db.taskDao().upgradeTask(task)


    }

    fun upgradeDescriptionTask (position: Int, description: String){

        val task = taskList[position]
        task.description = description
        db.taskDao().upgradeTask(task)
        taskAdapter.notifyDataSetChanged()


    }

    fun deleteTask (task: Task, position: Int){

        taskList.removeAt(position)
        db.taskDao().delete(task)
        taskAdapter.notifyDataSetChanged()


    }


}