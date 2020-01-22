package pzy64.xnotes.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class LastItemSpace(space: Int, layoutReversed: Boolean) : RecyclerView.ItemDecoration() {
    private var directSpace = 0
    private var reverseSpace = 0
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.bottom = directSpace
            outRect.top = reverseSpace
        }
    }

    init {
        if (layoutReversed) {
            directSpace = 0
            reverseSpace = space
        } else {
            directSpace = space
            reverseSpace = 0
        }
    }
}