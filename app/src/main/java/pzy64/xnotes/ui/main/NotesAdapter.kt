package pzy64.xnotes.ui.main

import android.content.res.ColorStateList
import android.graphics.Color
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import pzy64.xnotes.data.model.Note
import pzy64.xnotes.databinding.RowNoteBinding
import pzy64.xnotes.ui.Colors

class NotesAdapter(val data: List<Note>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowNoteBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(private val itemBinding: RowNoteBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(note: Note) {
            itemBinding.note = note
            val model = ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED,20f).build()
            val shapeDrawable = MaterialShapeDrawable(model)
            shapeDrawable.setStroke(4f, ColorStateList.valueOf(Colors.COLORS[note.color]))
            shapeDrawable.fillColor = ColorStateList.valueOf(Color.TRANSPARENT)
            ViewCompat.setBackground(itemBinding.containerLayout, shapeDrawable)
        }
    }
}