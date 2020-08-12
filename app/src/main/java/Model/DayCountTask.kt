package Model

import androidx.room.ColumnInfo

class DayCountTask(@ColumnInfo(name = "day") val day: String,
                   @ColumnInfo(name = "countDayTask") var countDayTask: Int,
                   @ColumnInfo(name = "countDoneDayTask") var countDoneDayTask: Int)

