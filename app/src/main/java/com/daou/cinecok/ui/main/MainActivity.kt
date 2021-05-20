package com.daou.cinecok.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.daou.cinecok.R
import com.daou.cinecok.databinding.ActivityMainBinding
import com.daou.cinecok.ui.main.home.HomeFragment
import com.daou.cinecok.ui.main.scrap.ScrapFragment
import com.daou.cinecok.ui.main.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var vpAdapter = MainVPAdapter(this)

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vmMain = ViewModelProvider.AndroidViewModelFactory(application).create(MainViewModel::class.java)
    }
    private fun initView() {
        vpAdapter.apply{
            addFragment(HomeFragment())
            addFragment(SearchFragment())
            addFragment(ScrapFragment())
        }

        binding.apply{
            //스와이프 막음 -> navigationBottom으로만 작동하도록.
            vpMain.isUserInputEnabled = false
            vpMain.adapter = vpAdapter

            navigationBottomMain.setOnNavigationItemSelectedListener { menuItem->
                when(menuItem.itemId) {
                    R.id.navigation_home -> vpMain.setCurrentItem(0, false)
                    R.id.navigation_search -> vpMain.setCurrentItem(1, false)
                    R.id.navigation_scrap -> vpMain.setCurrentItem(2, false)
                }

                true
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        initView()
    }
}