package ru.skillbranch.searchmovie.presentation.recycler_views.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemMovieOffsetDecoration(
    private val columnsNumber: Int,
    private val itemSize: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val column = position % columnsNumber
        val parentWidth = parent.width
        val spacing = (parentWidth - (itemSize * columnsNumber)) / (columnsNumber + 1)
        outRect.left = spacing - column * spacing / columnsNumber
        outRect.right = (column + 1) * spacing / columnsNumber
    }
}