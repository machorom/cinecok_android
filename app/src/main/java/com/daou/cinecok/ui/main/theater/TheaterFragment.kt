package com.daou.cinecok.ui.main.theater

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.daou.cinecok.R
import com.daou.cinecok.base.BaseAdapter
import com.daou.cinecok.base.BaseFragment
import com.daou.cinecok.databinding.FragmentTheaterBinding
import com.daou.cinecok.ui.main.theater.TheaterViewModel.Companion.VIEW_TYPE_LIST
import com.daou.cinecok.ui.main.theater.TheaterViewModel.Companion.VIEW_TYPE_MAP
import net.daum.mf.map.api.MapPoint
import org.koin.androidx.viewmodel.ext.android.viewModel

class TheaterFragment: BaseFragment<FragmentTheaterBinding>(), LocationListener {
    private val TAG = "TheaterFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_theater
    override val viewModel: TheaterViewModel by viewModel()
    private val rvAdapter = TheaterListAdapter()

    //참조 시점에 초기화
    private val mLocationManager: LocationManager by lazy {
        activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun initDataBinding() {
        Log.d(TAG, "initDataBinding: ${viewModel} , ${viewModel.theaterList.value?.size}")
        binding.vmTheater = viewModel
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        if (checkPermission()) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000L, 30f, this)
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000L, 30f, this)
        }
        else {
            Log.e(TAG, "onResume() called TT")
        }
    }

    override fun onPause() {
        super.onPause()
        mLocationManager.removeUpdates(this)
    }
    
    override fun initView() {
        with(binding) {
            rvTheaterResult.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            rvTheaterResult.adapter = rvAdapter.apply {
                setOnItemClick(object: BaseAdapter.OnItemClickListener {
                    override fun onItemClick(itemIndex: Int, viewitemID: Int) {
                        //지도로 이동
                        viewModel.theaterList.value?.get(itemIndex)?.let {item->
                            binding.mapContainer.mapView.setMapCenterPoint(
                                MapPoint.mapPointWithGeoCoord(item.latitude, item.longitude), false
                            )
                            viewModel.onChangeMode()
                        }

                    }
                })
            }
        }

        viewModel.crrViewType.observe(this, Observer {viewType->
            when(viewType) {
                VIEW_TYPE_LIST -> {
                    binding.rvTheaterResult.visibility = View.VISIBLE
                    binding.mapContainer.visibility = View.GONE
                }
                VIEW_TYPE_MAP -> {
                    binding.rvTheaterResult.visibility = View.GONE
                    binding.mapContainer.visibility = View.VISIBLE
                }
            }
        })

        viewModel.flagRefresh.observe(this, Observer {refresh->
            if (refresh && checkPermission()) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000L, 30f, this)
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000L, 30f, this)
            }
        })
    }

    override fun onLocationChanged(location: Location) {
        Log.d(TAG, "onLocationChanged: ${location.latitude} , ${location.longitude}")
        // OneTime Location Check
        mLocationManager.removeUpdates(this)

        //update Location
        viewModel.location = location
        viewModel.onUpdateLoaction(false)

        //Load Data
        viewModel.loadPage()

        //apply Map
        binding.mapContainer.initMap(location)
    }

    private fun checkPermission(): Boolean {
        activity?.let {
            if (ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
            return true
        }
        return false
    }
}