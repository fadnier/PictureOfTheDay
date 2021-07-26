package org.sochidrive.pod.ui.recycler

data class Note(
    val id: Int = 0,
    var title: String = "My title",
    var text: String? = "Text note",
    var priority: Int = 0
)
