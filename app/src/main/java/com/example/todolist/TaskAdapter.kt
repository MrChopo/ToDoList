package com.example.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import Model.Task

class TaskAdapter(private val items :MutableList<Task>, private val taskListActivity: TaskListActivity): RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View, private val context: Context, val taskListActivity: TaskListActivity) : RecyclerView.ViewHolder(itemView) {

        private val description: TextView = itemView.findViewById(R.id.itemDescription)
        private var switch: SwitchCompat = itemView.findViewById(R.id.itemSwitch)
        private var linerLayout: LinearLayout = itemView.findViewById(R.id.itemLayout)


        fun bind(item : Task, position: Int){

            description.text = item.description
            switch.isChecked = item.isDone

            switch.setOnCheckedChangeListener{ _, b: Boolean ->
                Toast.makeText(context, "Position: $position", Toast.LENGTH_LONG).show()
                //taskListActivity.addAndEditTask(false, item, position)
            }

            description.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "Text view: $position", Toast.LENGTH_LONG).show()
            })


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.task_list_item, parent, false)
        return MyViewHolder(view, parent.context, taskListActivity)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position], position)
    }
}
