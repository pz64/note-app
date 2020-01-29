package pzy64.xnotes.ui.screens.main

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import pzy64.xnotes.data.model.Note
import pzy64.xnotes.databinding.RowNoteBinding
import pzy64.xnotes.onDarkMode
import pzy64.xnotes.onLightMode
import pzy64.xnotes.scaleOutAnim
import pzy64.xnotes.ui.Colors

class NotesAdapter(
    private val onClick: (Note) -> Unit,
    private val selectedCallback: (selected: Boolean) -> Unit
) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    var data: MutableList<Note> = mutableListOf()

    fun deleteSelectedNotes() {

        var i = data.listIterator()

        while (i.hasNext()) {
            val note = i.next()
            if (note.isSelected) {
                i.remove()
                notifyItemRemoved(i.nextIndex())
            }
        }
    }

    fun deSelectAll() {
        for (i in data)
            i.isSelected = false

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowNoteBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(data[position])
        holder.itemView.scaleOutAnim()
    }

    inner class ViewHolder(private val itemBinding: RowNoteBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(note: Note) {
            itemBinding.note = note
            setCardColor(note, itemBinding)
            itemBinding.containerLayout.setOnClickListener {
                onClick(note)
            }

            itemBinding.containerLayout.setOnLongClickListener {
                note.isSelected = !note.isSelected

                setCardColor(note, itemBinding)

                if (note.isSelected)
                    selectedCallback(true)
                else {
                    var selected = false
                    for (i in data) {
                        if (i.isSelected) {
                            selected = true
                            break
                        }
                    }
                    selectedCallback(selected)
                }

                true
            }

        }
    }

    private fun setCardColor(note: Note, binding: RowNoteBinding) {
        val model =
            ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 20f).build()
        val shapeDrawable = MaterialShapeDrawable(model)

        val strokeColor = if (Colors.bg(note.color, 0xff) == Colors.bg(
                0,
                0xff
            )
        ) 0xffbababa.toInt() else Colors.bg(note.color, 0xff)

        if (note.isSelected) {

            val alpha = 0xff

            val color = Colors.bg(note.color, alpha)

            shapeDrawable.fillColor = ColorStateList.valueOf(color)
            ViewCompat.setBackground(binding.containerLayout, shapeDrawable)

            if (Colors.isDark(note.color, alpha)) {
                binding.title.setTextColor(0xffffffff.toInt())
                binding.content.setTextColor(0xffffffff.toInt())
                binding.lastUpdated.setTextColor(0xfffafafa.toInt())
            } else {
                binding.title.setTextColor(0xff000000.toInt())
                binding.content.setTextColor(0xff000000.toInt())
                binding.lastUpdated.setTextColor(0xff757575.toInt())
            }

        } else {
            val alpha = 0x2b
            shapeDrawable.setStroke(2f, ColorStateList.valueOf(strokeColor))
            shapeDrawable.fillColor = ColorStateList.valueOf(Colors.bg(note.color, alpha))
            ViewCompat.setBackground(binding.containerLayout, shapeDrawable)

            binding.root.context.onLightMode {
                binding.title.setTextColor(0xff000000.toInt())
                binding.content.setTextColor(0xff000000.toInt())
                binding.lastUpdated.setTextColor(0xff757575.toInt())
            }
            binding.root.context.onDarkMode {
                binding.title.setTextColor(0xffffffff.toInt())
                binding.content.setTextColor(0xffffffff.toInt())
                binding.lastUpdated.setTextColor(0xfffafafa.toInt())
            }
        }
    }
}