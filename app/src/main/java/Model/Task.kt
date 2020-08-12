package Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
class Task(@PrimaryKey(autoGenerate = true) var id: Int,
           @ColumnInfo(name = "description") val description: String,
           @ColumnInfo(name = "isDone") val isDone: Boolean)


