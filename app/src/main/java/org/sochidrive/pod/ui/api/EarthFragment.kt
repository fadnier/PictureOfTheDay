package org.sochidrive.pod.ui.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import kotlinx.android.synthetic.main.fragment_earth.*
import org.sochidrive.pod.R
import org.sochidrive.pod.ui.picture.PictureOfTheDayData
import org.sochidrive.pod.ui.picture.PictureOfTheDayModel
import java.text.SimpleDateFormat
import java.util.*

class EarthFragment : Fragment() {

    private val viewModel : PictureOfTheDayModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val formatDate = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        c.add(Calendar.DATE,-1)
        val yesterdayDay = formatDate.format(c.getTime())
        viewModel.getData(yesterdayDay).observe(this, Observer<PictureOfTheDayData>{ renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData

                val url = serverResponseData.url

                if(url.isNullOrEmpty()) {

                } else {
                    picture?.load(url) {
                        lifecycle(this@EarthFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }

            }

            is PictureOfTheDayData.Loading -> {

            }

            is PictureOfTheDayData.Error -> {

            }
        }
    }
}