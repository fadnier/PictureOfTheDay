package org.sochidrive.pod.ui.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_navigarion_layout.*
import org.sochidrive.pod.R
import org.sochidrive.pod.ui.clip.ChipsFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_navigarion_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,
                    ChipsFragment())?.addToBackStack(null)?.commit()
                R.id.navigation_two -> {
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,
                        PictureOfTheDayFragment())?.addToBackStack(null)?.commit()
                }
            }

            true
        }
    }
}