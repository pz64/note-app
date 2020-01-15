package pzy64.xnotes.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pzy64.xnotes.data.TABLE_NAME


@Entity(tableName = TABLE_NAME)
class Note(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "title")val title:String,
    @ColumnInfo(name ="content") val description:String,
    @ColumnInfo(name= "color") val color:Int = 0,
    @ColumnInfo(name = "last_updated") val lastUpdated:Long
)