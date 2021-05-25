package com.daou.cinecok.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.daou.cinecok.R
import com.daou.cinecok.databinding.ActivityMainBinding
import com.daou.cinecok.ui.main.home.HomeFragment
import com.daou.cinecok.ui.main.scrap.ScrapFragment
import com.daou.cinecok.ui.main.search.SearchFragment
import com.daou.cinecok.ui.main.theater.TheaterFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var vpAdapter = MainVPAdapter(this)

    val locationReqCode = 100

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
            addFragment(TheaterFragment())
        }

        binding.apply{
            //스와이프 막음 -> navigationBottom으로만 작동하도록.
            vpMain.isUserInputEnabled = false
            // TODO Temp Page Limit
            vpMain.offscreenPageLimit = 4
            vpMain.adapter = vpAdapter

            navigationBottomMain.setOnNavigationItemSelectedListener { menuItem->
                when(menuItem.itemId) {
                    R.id.navigation_home -> vpMain.setCurrentItem(0, false)
                    R.id.navigation_search -> vpMain.setCurrentItem(1, false)
                    R.id.navigation_scrap -> vpMain.setCurrentItem(2, false)
                    R.id.navigation_theater -> {
                        permitAndMove()
                    }
                }

                true
            }
        }
    }

    private fun permitAndMove() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    binding.vpMain.setCurrentItem(3, false)
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    Toast.makeText(this, "해주세요", Toast.LENGTH_SHORT).show()
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:$packageName"))
                        .apply {
                            startActivity(this);
                        }
                }
                else -> {
                    // You can directly ask for the permission.
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        locationReqCode
                    )
                }
            }
        } else {
            binding.vpMain.setCurrentItem(3, false)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            locationReqCode -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    permitAndMove()
                } else {
                    Toast.makeText(this, "해주세용 TT", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        initView()
    }
}