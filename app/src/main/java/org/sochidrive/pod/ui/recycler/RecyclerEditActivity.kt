package org.sochidrive.pod.ui.recycler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_recycler_add.*
import org.sochidrive.pod.R


class RecyclerEditActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_recycler_add)
        rec_add.setOnClickListener {
            val result = Intent()
            result.putExtra("TITLE_SELECT", input_title_add.text.toString())
            result.putExtra("TEXT_SELECT", input_text_add.text.toString())
            setResult(RESULT_OK, result)
            finish()
        }
    }
}