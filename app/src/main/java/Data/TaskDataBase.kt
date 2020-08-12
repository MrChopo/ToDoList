package Data

import Model.Task
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Task::class), version = 1)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDAO
}