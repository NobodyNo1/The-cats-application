package com.bekarys.tech_assignment.thecats.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bekarys.tech_assignment.thecats.R
import com.bekarys.tech_assignment.thecats.application.ApplicationComponentHolder
import com.bekarys.tech_assignment.thecats.databinding.ActivityMainBinding
import com.bekarys.tech_assignment.thecats.di.main.DaggerMainComponent
import com.bekarys.tech_assignment.thecats.features.breedlist.presentation.view.BreedListFragment
import com.bekarys.tech_assignment.thecats.features.common.extensions.alert
import com.bekarys.tech_assignment.thecats.features.common.permission.*
import com.bekarys.tech_assignment.thecats.features.favorites.presentation.view.FavoritesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), PermissionListener, PermissionController {

    private val fragmentList: List<MainNavigationFragmentData> = listOf(
        MainNavigationFragmentData(lazy { BreedListFragment() }),
        MainNavigationFragmentData(lazy { FavoritesFragment() })
    )
    private val tabViewIds = listOf(
        R.id.navigation_cats,
        R.id.navigation_favorites
    )

    @Inject
    lateinit var permissionHelper: PermissionHelper
    lateinit var binding: ActivityMainBinding

    lateinit var viewPagerAdapter: PagerAdapter

    private lateinit var permissionGrantedListener: PermissionGrantedListener

    private val navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            val clickedItem = tabViewIds.firstOrNull { it == menuItem.itemId } ?: false
            val position = tabViewIds.indexOf(clickedItem)
            selectTab(position)

            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDI()
        permissionHelper.initialize(this)

        initTabs()
        binding.bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        selectTab(0)
    }

    private fun setupDI() {
        DaggerMainComponent.builder()
            .appComponent(ApplicationComponentHolder.component)
            .build()
            .inject(this)
    }

    private fun initTabs() {
        this.viewPagerAdapter = PagerAdapter(this, fragmentList)
        binding.navigationViewPager.adapter = viewPagerAdapter
        binding.navigationViewPager.isUserInputEnabled = false
    }

    private fun selectTab(position: Int) {
        binding.navigationViewPager.setCurrentItem(position)
    }

    override fun askPermission(
        config: PermissionConfig,
        listener: PermissionGrantedListener
    ) {
        this.permissionGrantedListener = listener
        permissionHelper.makeAction(config, this)
    }

    override fun onPermissionGranted(permissionConfig: PermissionConfig) {
        permissionGrantedListener.onPermissionGranted(permissionConfig)
    }

    override fun onShowExplanationDialog(permissionConfig: PermissionConfig) {
        alert("Permission Denied", permissionConfig.explanationPermissionMessage) {
        }
    }

    override fun onShowNavigateToSettingsDialog(permissionConfig: PermissionConfig) {
        alert("Permission Denied", permissionConfig.explanationPermissionMessage) {
            permissionHelper.goToSettings()
        }
    }
}
