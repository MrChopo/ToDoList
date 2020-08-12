package Data

import Model.DayCountTask
import Model.Task
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDAO {

        @Query("SELECT * FROM tasks")
        fun getAll(): MutableList<Task>

        @Query("SELECT day, COUNT(id) AS countDayTask, SUM(isDone) AS countDoneDayTask FROM tasks GROUP BY day")
        fun getAllInDay(): MutableList<DayCountTask>

        @Query("SELECT * FROM tasks WHERE day = :day")
        fun getAllOnDay(day: String): MutableList<Task>

        @Query("SELECT * FROM tasks WHERE id IN (:taskIds)")
        fun loadAllByIds(taskIds: IntArray): MutableList<Task>

        @Query("SELECT * FROM tasks WHERE id = :taskId")
        fun getTaskById(taskId: Long): Task

        @Update
        fun upgradeTask(task: Task)

        @Insert
        fun insertAll(vararg tasks: Task)

        @Insert
        fun insertTask(task: Task): Long

        @Delete
        fun delete(task: Task)

}