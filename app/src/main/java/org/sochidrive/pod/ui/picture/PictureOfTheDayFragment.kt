package org.sochidrive.pod.ui.picture

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.main_fragment.*
import org.sochidrive.pod.MainActivity
import org.sochidrive.pod.R
import org.sochidrive.pod.ui.animate.AnimateFragment
import org.sochidrive.pod.ui.apibottom.ApiBottomActivity
import org.sochidrive.pod.ui.clip.ChipsFragment
import org.sochidrive.pod.ui.settings.SettingsFragment
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var isImageAnimate = false
    private var isChipSelect1 = false
    private var isChipSelect2 = false
    private var isChipSelect3 = false

    private val viewModel : PictureOfTheDayModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayModel::class.java)
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))

        initChipsRequest()
        init();

        setBootomAppBar(view)
    }

    private fun init() {
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
        image_view.setOnClickListener {
            if(!isImageAnimate) {
                isImageAnimate = true
                it.animate()
                    .rotation(360F)
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .translationY(100f)
                    .setDuration(300)
            } else {
                isImageAnimate = false
                it.animate()
                    .rotation(-360f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .translationY(0f)
                    .setDuration(300)
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val formatDate = SimpleDateFormat("yyyy-MM-dd")
        val myDate = formatDate.format(Date())

        viewModel.getData(myDate).observe(viewLifecycleOwner, Observer<PictureOfTheDayData>{ renderData(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.app_bar_cl-> activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,
                AnimateFragment())?.addToBackStack(null)?.commit()
            R.id.app_bar_fav -> activity?.let { startActivity(Intent(it, ApiBottomActivity::class.java)) }
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,
                SettingsFragment())?.addToBackStack(null)?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager,"tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initChipsRequest() {
        val formatDate = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        val myDate = formatDate.format(Date())
        c.add(Calendar.DATE,-1)
        val yesterdayDay = formatDate.format(c.getTime())
        c.add(Calendar.DATE,-1)
        val beforeYesterdayDay = formatDate.format(c.getTime())

        chipToday.setOnClickListener {
            toast("Change today "+myDate.toString());
            viewModel.getData(myDate).observe(viewLifecycleOwner, Observer<PictureOfTheDayData>{ renderData(it) })
            isChipSelect1=true
            it.animate()
                .y(-10f)
                .setDuration(100)
            if(isChipSelect2) {
                chipBeforeYesterday.animate()
                    .y(10f)
                isChipSelect2=false
            }
            if(isChipSelect3) {
                chipYesterday.animate()
                    .y(10f)
                isChipSelect3=false
            }
        }

        chipBeforeYesterday.setOnClickListener {
            toast("Change day before yesterday "+beforeYesterdayDay.toString());
            viewModel.getData(beforeYesterdayDay).observe(viewLifecycleOwner, Observer<PictureOfTheDayData>{ renderData(it) })
            isChipSelect2=true
            it.animate()
                .y(-10f)
                .setDuration(100)
            if(isChipSelect1) {
                chipToday.animate()
                    .y(10f)
                isChipSelect1=false
            }
            if(isChipSelect3) {
                chipYesterday.animate()
                    .y(10f)
                isChipSelect3=false
            }
        }

        chipYesterday.setOnClickListener {
            toast("Change yesterday "+yesterdayDay.toString());
            viewModel.getData(yesterdayDay).observe(viewLifecycleOwner, Observer<PictureOfTheDayData>{ renderData(it) })
            isChipSelect3=true
            it.animate()
                .y(-10f)
                .setDuration(100)
            if(isChipSelect1) {
                chipToday.animate()
                    .y(10f)
                isChipSelect1=false
            }
            if(isChipSelect2) {
                chipBeforeYesterday.animate()
                    .y(10f)
                isChipSelect2=false
            }
        }
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData

                val url = serverResponseData.url
                val explanation = serverResponseData.explanation
                val title = serverResponseData.title

                if(url.isNullOrEmpty()) {
                    toast("Link is Empty")
                } else {
                    image_view.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }

                explanation?.let { bottom_sheet_description.text = it }
                title?.let { bottom_sheet_description_header.text = it }

            }

            is PictureOfTheDayData.Loading -> {

            }

            is PictureOfTheDayData.Error -> {
                toast(data.error.message)
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                toast("onSlide")
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> toast("STATE_DRAGGING")
                    BottomSheetBehavior.STATE_COLLAPSED -> toast("STATE_COLLAPSED")
                    BottomSheetBehavior.STATE_EXPANDED -> toast("STATE_EXPANDED")
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> toast("STATE_HALF_EXPANDED")
                    BottomSheetBehavior.STATE_HIDDEN -> toast("STATE_HIDDEN")
                    BottomSheetBehavior.STATE_SETTLING -> toast("STATE_SETTLING")
                }
            }

        })
    }

    private fun setBootomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)

        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                bottom_app_bar.navigationIcon = null
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                bottom_sheet_container.visibility = View.VISIBLE
            } else {
                isMain = true
                bottom_app_bar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar)
                bottom_sheet_container.visibility = View.GONE
            }
        }
    }
}