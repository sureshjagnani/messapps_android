package com.messapps


import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat.START
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.messapps.adapter.ChildAdapter
import com.messapps.adapter.NaviAdapter
import com.messapps.databinding.ActivityMainBinding
import com.messapps.data.model.NavigationData
import com.messapps.ui.builds.BuildsFrag
import com.messapps.ui.estimates.EstimatesFrag
import com.messapps.ui.home.HomeFrag
import com.messapps.ui.invoices.InvoicesFrag
import com.messapps.ui.profile.ProfileFrag
import com.messapps.ui.progress.ProgressFrag
import com.messapps.utils.Call_Contact
import com.messapps.utils.Call_Profile
import com.messapps.utils.Last_Selection
import com.messapps.utils.Theme
import com.messapps.utils.UserPreferencesRepository
import com.messapps.utils.getData


class MainActivity : AppCompatActivity(), NaviAdapter.ClickCallback, ChildAdapter.CallbackChild {
    private var themePosition: Int? = null

    private var userPrefs: UserPreferencesRepository = MyApp.instance.userPreferences
    private lateinit var binding: ActivityMainBinding
    private var navigationAdapter: NaviAdapter? = null
    var expandableListTitle: MutableList<NavigationData>? = null
    var expandableListDetail: HashMap<String, List<NavigationData>>? = null
    private var mBarVisible: Boolean = true
    private val mHome = 1
    private val mEstimates = 2
    private val mProgress = 3
    private val mBuilds = 4
    private val mInvoice = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toggle = ActionBarDrawerToggle(
            this@MainActivity,
            binding.drawer,
            null,
            R.string.navigation_drawer_close,
            R.string.navigation_drawer_close
        )
        binding.drawer.setDrawerListener(toggle)
        toggle.syncState()

        binding.llMenu.setOnClickListener {
            openCloseDrawer()
        }
        binding.llClose.setOnClickListener {
            openCloseDrawer()
        }
        binding.customLayout.llLight.setOnClickListener {
            setAppTheme(0)
        }
        binding.customLayout.llDark.setOnClickListener {
            setAppTheme(1)
        }
        binding.tvSignout.setOnClickListener {
            openCloseDrawer()
            finish()
        }
        binding.llHome.setOnClickListener {
            replaceFragment(HomeFrag())
            barSelection(mHome)
        }
        binding.llEstimate.setOnClickListener {
            replaceFragment(EstimatesFrag())
            barSelection(mEstimates)
        }
        binding.llProgress.setOnClickListener {
            replaceFragment(ProgressFrag())
            barSelection(mProgress)
        }
        binding.llInvoice.setOnClickListener {
            replaceFragment(InvoicesFrag())
            barSelection(mInvoice)
        }
        binding.llBuild.setOnClickListener {
            replaceFragment(BuildsFrag())
            barSelection(mBuilds)

        }
        replaceNavigationFragment()

        initTheme()

        barSelection(mHome)
        replaceFragment(HomeFrag())
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.drawer.isDrawerOpen(START)) {
            binding.drawer.closeDrawer(START)
        } else {
            super.onBackPressed()
        }
    }

    private fun openCloseDrawer() {
        if (binding.drawer.isDrawerOpen(START)) binding.drawer.closeDrawer(START)
        else binding.drawer.openDrawer(START)
    }

    private fun initTheme() {
        themePosition = when (userPrefs.appTheme) {
            Theme.LIGHT_MODE -> 0
            Theme.DARK_MODE -> 1
            else -> 0
        }
        setAppTheme(themePosition!!)
    }

    private fun setAppTheme(themePosition: Int) {
        userPrefs.updateTheme(
            when (themePosition) {
                0 -> Theme.LIGHT_MODE
                1 -> Theme.DARK_MODE
                else -> Theme.LIGHT_MODE
            }
        )
    }

    private fun replaceNavigationFragment() {
        binding.rvNavigation.setHasFixedSize(true)
        val layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvNavigation.layoutManager = layoutManager
        expandableListDetail = getData()
        expandableListTitle = com.messapps.utils.getTitle()// ArrayList(expandableListDetail!!.keys)
        navigationAdapter = NaviAdapter(
            this@MainActivity,
            expandableListTitle!!,
            expandableListDetail!!,
            this,
            this
        )
        binding.rvNavigation.setAdapter(navigationAdapter!!)
        //supportFragmentManager.beginTransaction().replace(binding.flContainerNavigationMenu.id, newInstance("test","test"), "Navigation").commit()
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(binding.flContainerFragment.id, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun callNaviFragment(pos: Int?, order: NavigationData) {
        //Log.e("parent call", "parent calling")
        openCloseDrawer()
        if (pos == Call_Profile) {
            barLayoutVisibility(1)
            replaceFragment(ProfileFrag())
        } else if (pos == Call_Contact) {
            barLayoutVisibility(1)
        } else {
            callFragFromNavigation(order)
        }
    }

    private fun callFragFromNavigation(order: NavigationData) {
        when (order.insertBy) {
            mHome -> {
                replaceFragment(HomeFrag())
                barSelection(mHome)
            }

            mEstimates -> {
                replaceFragment(EstimatesFrag())
                barSelection(mEstimates)
            }

            mProgress -> {
                replaceFragment(ProgressFrag())
                barSelection(mProgress)
            }

            mBuilds -> {
                replaceFragment(BuildsFrag())
                barSelection(mBuilds)
            }

            mInvoice -> {
                replaceFragment(InvoicesFrag())
                barSelection(mInvoice)
            }

            else -> {
                replaceFragment(HomeFrag())
                barSelection(mHome)
            }
        }
    }

    private fun barLayoutVisibility(mShow: Int) {
        when (mShow) {
            0 -> {
                mBarVisible = true
                binding.llBar.visibility = View.VISIBLE
            }

            1 -> {
                mBarVisible = false
                binding.llBar.visibility = View.GONE
            }

            else -> {
                mBarVisible = true
                binding.llBar.visibility = View.VISIBLE
            }
        }

    }

    private fun setBarLastPosition() {
        barSelection(Last_Selection)
    }

    override fun viewDetailChild(order: NavigationData) {
        //Log.e("child call", "child calling")
        openCloseDrawer()
        if (!mBarVisible) {
            barLayoutVisibility(0)
        }
        callFragFromNavigation(order)
    }

    private fun barSelection(mShow: Int) {
        Last_Selection = mShow
        if (mShow == mHome) {
            changeTextVisibility(
                binding.tvHome,
                binding.tvEstimates,
                binding.tvProgress,
                binding.tvBuilds,
                binding.tvInvoices
            )
            changeBg(
                binding.llHome,
                binding.llEstimate,
                binding.llProgress,
                binding.llBuild,
                binding.llInvoice
            )
            changeImageSelected(
                R.drawable.ic_bar_home_sel,
                R.drawable.ic_bar_estimate,
                R.drawable.ic_bar_progress,
                R.drawable.ic_bar_build,
                R.drawable.ic_bar_invoice
            )

        }
        if (mShow == mEstimates) {
            changeTextVisibility(
                binding.tvEstimates,
                binding.tvHome,
                binding.tvProgress,
                binding.tvBuilds,
                binding.tvInvoices
            )
            changeBg(
                binding.llEstimate,
                binding.llHome,
                binding.llProgress,
                binding.llBuild,
                binding.llInvoice
            )
            changeImageSelected(
                R.drawable.ic_bar_home,
                R.drawable.ic_bar_estimate_sel,
                R.drawable.ic_bar_progress,
                R.drawable.ic_bar_build,
                R.drawable.ic_bar_invoice
            )
        }
        if (mShow == mProgress) {
            changeTextVisibility(
                binding.tvProgress,
                binding.tvHome,
                binding.tvEstimates,
                binding.tvBuilds,
                binding.tvInvoices
            )
            changeBg(
                binding.llProgress,
                binding.llHome,
                binding.llEstimate,
                binding.llBuild,
                binding.llInvoice
            )
            changeImageSelected(
                R.drawable.ic_bar_home,
                R.drawable.ic_bar_estimate,
                R.drawable.ic_bar_progress_sel,
                R.drawable.ic_bar_build,
                R.drawable.ic_bar_invoice
            )
        }
        if (mShow == mBuilds) {
            changeTextVisibility(
                binding.tvBuilds,
                binding.tvHome,
                binding.tvEstimates,
                binding.tvProgress,
                binding.tvInvoices
            )
            changeBg(
                binding.llBuild,
                binding.llHome,
                binding.llEstimate,
                binding.llProgress,
                binding.llInvoice
            )
            changeImageSelected(
                R.drawable.ic_bar_home,
                R.drawable.ic_bar_estimate,
                R.drawable.ic_bar_progress,
                R.drawable.ic_bar_build_sel,
                R.drawable.ic_bar_invoice
            )
        }
        if (mShow == mInvoice) {
            changeTextVisibility(
                binding.tvInvoices,
                binding.tvHome,
                binding.tvEstimates,
                binding.tvProgress,
                binding.tvBuilds
            )
            changeBg(
                binding.llInvoice,
                binding.llBuild,
                binding.llHome,
                binding.llEstimate,
                binding.llProgress
            )
            changeImageSelected(
                R.drawable.ic_bar_home,
                R.drawable.ic_bar_estimate,
                R.drawable.ic_bar_progress,
                R.drawable.ic_bar_build,
                R.drawable.ic_bar_invoice_sel
            )
        }

    }

    private fun changeBg(
        l1: LinearLayoutCompat,
        l2: LinearLayoutCompat,
        l3: LinearLayoutCompat,
        l4: LinearLayoutCompat,
        l5: LinearLayoutCompat
    ) {
        l1.background =
            AppCompatResources.getDrawable(this@MainActivity, R.drawable.bg_bar_selected)
        l2.background =
            AppCompatResources.getDrawable(this@MainActivity, R.drawable.bg_bar_bottom_visible)
        l3.background =
            AppCompatResources.getDrawable(this@MainActivity, R.drawable.bg_bar_bottom_visible)
        l4.background =
            AppCompatResources.getDrawable(this@MainActivity, R.drawable.bg_bar_bottom_visible)
        l5.background =
            AppCompatResources.getDrawable(this@MainActivity, R.drawable.bg_bar_bottom_visible)
    }

    private fun changeImageSelected(id1: Int, id2: Int, id3: Int, id4: Int, id5: Int) {
        binding.ivHome.setImageResource(id1)
        binding.ivEstimates.setImageResource(id2)
        binding.ivProgress.setImageResource(id3)
        binding.ivBuilds.setImageResource(id4)
        binding.ivInvoice.setImageResource(id5)
    }

    private fun changeTextVisibility(
        t1: AppCompatTextView,
        t2: AppCompatTextView,
        t3: AppCompatTextView,
        t4: AppCompatTextView,
        t5: AppCompatTextView
    ) {
        t1.visibility = View.VISIBLE
        changeTextColor(t1)
        t2.visibility = View.GONE
        t3.visibility = View.GONE
        t4.visibility = View.GONE
        t5.visibility = View.GONE
    }

    public fun updateChildFragment() {
        //Log.e("UpdateFrag", "Calling from Frag")
    }

    private fun changeTextColor(t1: AppCompatTextView) {
        val themePosition = when (userPrefs.appTheme) {
            Theme.LIGHT_MODE -> 0
            Theme.DARK_MODE -> 1
            else -> 0
        }
        if (themePosition == 0)
            t1.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
        else
            t1.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.active_btn))
    }

}