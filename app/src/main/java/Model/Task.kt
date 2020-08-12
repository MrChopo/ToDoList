package Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
class Task(@PrimaryKey(autoGenerate = true) var id: Int,
           @ColumnInfo(name = "description") var description: String,
           @ColumnInfo(name = "isDone") var isDone: Boolean,
           @ColumnInfo(name = "day") var day: String)



