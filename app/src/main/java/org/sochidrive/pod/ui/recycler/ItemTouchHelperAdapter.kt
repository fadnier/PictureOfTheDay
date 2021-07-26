package org.sochidrive.pod.ui.recycler

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}