package com.daou.cinecok.ui.main.theater

import android.content.Context
import android.location.Location
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.daou.cinecok.data.restapi.KTheaterData
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class MapContainer(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    var mapView: MapView = MapView(context)
    var myMarker: MapPOIItem? = null
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addView(mapView)
    }

    fun initMap(location: Location) {
        myMarker?.let {marker->
            mapView.removePOIItem(marker)
        }
        //myMarker
        myMarker = MapPOIItem().apply {
            itemName = "ME"
            mapPoint = MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude)
            markerType = MapPOIItem.MarkerType.YellowPin
            selectedMarkerType = MapPOIItem.MarkerType.YellowPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            mapView.addPOIItem(this)
        }

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude), false)
    }

    private fun addMarker(data: KTheaterData) {
        val marker = MapPOIItem().apply {
            itemName = data.placeName
            mapPoint = MapPoint.mapPointWithGeoCoord(data.latitude, data.longitude)
            markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
            selectedMarkerType =
                MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            mapView.addPOIItem(this)
        }
    }

    fun addMarkers(data: MutableList<KTheaterData>) {
        repeat(data.size) { idx->
            addMarker(data[idx])
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeAllViews()
    }

}