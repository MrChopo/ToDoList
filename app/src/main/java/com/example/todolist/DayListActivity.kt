package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class DayListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_list)

        val cal: Calendar = Calendar.getInstance()
        cal.setTime(Date())
        val pattern = "yy.MM.dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val dateList: MutableList<String> = mutableListOf()

        for (i in 0..20){
            //Log.v("CalendarDay", cal.time.toString())
            dateList.add(simpleDateFormat.format(cal.time))
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }

        val recyclerView: RecyclerView = findViewById(R.id.dayRecyclerView)
        val dayAdapter = DayListAdapter(dateList)

        recyclerView.adapter = dayAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }
}