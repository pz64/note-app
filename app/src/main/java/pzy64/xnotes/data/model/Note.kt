package pzy64.xnotes.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pzy64.xnotes.data.TABLE_NAME

const val COLUMN_TITLE  = "title"
const val COLUMN_CONTENT = "content"
const val COLUMN_COLOR = "color"
const val COLUMN_FONT = "font"
const val COLUMN_LASTUPDATED = "last_updated"

@Entity(tableName = TABLE_NAME)
class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = COLUMN_TITLE) var title: String,

    @ColumnInfo(name = COLUMN_CONTENT) var content: String,

    @ColumnInfo(name = COLUMN_COLOR) var color: Int = 0,

    @ColumnInfo(name = COLUMN_FONT) var font: Int = 0,

    @ColumnInfo(name = COLUMN_LASTUPDATED) var lastUpdated: Long
)