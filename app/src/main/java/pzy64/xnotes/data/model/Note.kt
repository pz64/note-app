package pzy64.xnotes.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pzy64.xnotes.data.TABLE_NAME

const val COLUMN_ID = "uid"
const val COLUMN_TITLE = "title"
const val COLUMN_CONTENT = "content"
const val COLUMN_COLOR = "color"
const val COLUMN_FONT = "font"
const val COLUMN_LASTUPDATED = "last_updated"

@Entity(tableName = TABLE_NAME)
data class Note(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = COLUMN_ID) val id: Int = 0,

    @ColumnInfo(name = COLUMN_TITLE) var title: String,

    @ColumnInfo(name = COLUMN_CONTENT) var content: String,

    @ColumnInfo(name = COLUMN_COLOR) var color: Int = 0,

    @ColumnInfo(name = COLUMN_FONT) var font: Int = 0,

    @ColumnInfo(name = COLUMN_LASTUPDATED) var lastUpdated: Long

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeInt(color)
        parcel.writeInt(font)
        parcel.writeLong(lastUpdated)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}