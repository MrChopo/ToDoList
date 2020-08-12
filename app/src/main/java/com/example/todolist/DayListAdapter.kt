package com.example.todolist

import Model.DayCountTask
import Model.Task
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView

class DayListAdapter(private val dayList: MutableList<DayCountTask>):
    RecyclerView.Adapter<DayListAdapter.MyDayViewHolder>() {

    class MyDayViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {

        private val day: TextView = itemView.findViewById(R.id.dayItemTextView)
        private val dayCountTask: TextView = itemView.findViewById(R.id.dayCountTaskItemTextView)
        private val dayCountDoneTask: TextView = itemView.findViewById(R.id.dayCountDoneTaskItemTextView)
        private val dayLinearLayout: LinearLayout = itemView.findViewById(R.id.dayLinearLayout)

        fun bind(item : DayCountTask) {
            day.text = item.day
            dayCountTask.text = "All: ${item.countDayTask}"
            dayCountDoneTask.text = "Done: ${item.countDoneDayTask}"

            dayLinearLayout.setOnClickListener{
                val intent = Intent(context, TaskListActivity::class.java).apply {
                    putExtra("day", item.day)
                }
                context.startActivity(intent)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.day_list_item, parent, false)
        return DayListAdapter.MyDayViewHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    override fun onBindViewHolder(holder: MyDayViewHolder, position: Int) {
        holder.bind(dayList[position])
    }
}