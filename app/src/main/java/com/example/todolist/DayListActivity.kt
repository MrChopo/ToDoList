package com.example.todolist

import Data.TaskDataBase
import Model.DayCountTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import java.text.SimpleDateFormat
import java.util.*

class DayListActivity : AppCompatActivity() {

    lateinit var db: TaskDataBase
    lateinit var dayAdapter: DayListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_list)

        db = Room.databaseBuilder(
            applicationContext,
            TaskDataBase::class.java, "taskDB").allowMainThreadQueries().build()

        val dayCountTask: MutableList<DayCountTask> = db.taskDao().getAllInDay()
        for (item in dayCountTask){
            Log.v("CalendarDayCount", item.day)
            Log.v("CalendarDayCount", item.countDayTask.toString())
            Log.v("CalendarDayCount", item.countDoneDayTask.toString())
        }


        val cal: Calendar = Calendar.getInstance()
        cal.setTime(Date())
        val pattern = "yy.MM.dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val dateList: MutableList<DayCountTask> = mutableListOf()

        for (i in 0..13){
            //Log.v("CalendarDay", cal.time.toString())
            var dayItem: DayCountTask = DayCountTask(simpleDateFormat.format(cal.time), 0, 0)

            for (day in dayCountTask){
                if(dayItem.day == day.day){
                    dayItem.countDayTask = day.countDayTask
                    dayItem.countDoneDayTask = day.countDoneDayTask
                }
            }
            dateList.add(dayItem)
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }


        val recyclerView: RecyclerView = findViewById(R.id.dayRecyclerView)
        dayAdapter = DayListAdapter(dateList)

        recyclerView.adapter = dayAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }

    override fun onStart() {
        super.onStart()
        dayAdapter.notifyDataSetChanged()
    }
}