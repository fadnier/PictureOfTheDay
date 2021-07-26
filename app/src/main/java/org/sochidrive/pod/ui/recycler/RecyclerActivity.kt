package org.sochidrive.pod.ui.recycler

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.activity_recycler_item_earth.view.*
import kotlinx.android.synthetic.main.activity_recycler_item_mars.view.*
import org.sochidrive.pod.R


class RecyclerActivity : AppCompatActivity() {
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var adapter: RecyclerActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        val data = arrayListOf(
            Pair(Note(1, "Note 1", "Text 1", 3), false)
        )

        data.add(0, Pair(Note(0, "Header"), false))

        adapter = RecyclerActivityAdapter(
            object : RecyclerActivityAdapter.OnListItemClickListener {
                override fun onItemClick(note: Note) {
                    Toast.makeText(this@RecyclerActivity, note.title, Toast.LENGTH_SHORT).show()
                }
            },
            data,
            object : RecyclerActivityAdapter.OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )

        recyclerView.adapter = adapter
        recyclerActivityFAB.setOnClickListener {
            //adapter.appendItem()
            startActivityForResult(Intent(this,RecyclerEditActivity::class.java),99)
        }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerActivityDiffUtilFAB.setOnClickListener { changeAdapterData() }
        //changeAdapterData()
    }

    override fun onActivityResult(requestCode: Int, responseCode: Int, result: Intent?) {
        super.onActivityResult(requestCode, responseCode, result)
        if (result != null && responseCode == RESULT_OK) {
            val title = result.getStringExtra("TITLE_SELECT")
            val text = result.getStringExtra("TEXT_SELECT")
            adapter.appendItem(Note(1,title.toString(),text.toString()))
        }
    }

    private fun changeAdapterData() {
        adapter.setItems(createItemList().map { it })
    }

    private fun createItemList(): List<Pair<Note, Boolean>> {
        return listOf(
                Pair(Note(0, "Header"), false),
                Pair(Note(1, "Note 1", "Text 1", 2), false),
                Pair(Note(2, "Note 2", "Text 2", 1), false),
                Pair(Note(3, "Note 3", "Text 3", 1), false),
                Pair(Note(4, "Note 4", "Text 4", 0), false),
                Pair(Note(5, "Note 5", "Text 5", 0), false),
                Pair(Note(6, "Note 6", "Text 6", 0), false)
            )

    }
}