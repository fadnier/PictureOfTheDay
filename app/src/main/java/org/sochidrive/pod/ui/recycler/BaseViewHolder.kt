package org.sochidrive.pod.ui.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(dataItem: Pair<Note, Boolean>)
}