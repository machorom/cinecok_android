package com.daou.cinecok.ui.main.theater

import androidx.databinding.BindingAdapter
import com.daou.cinecok.data.restapi.KTheaterData

object MapBindAdapter {
    @JvmStatic
    @BindingAdapter("markerData")
    fun drawMarker(container: MapContainer, data: MutableList<KTheaterData>) {
        container.addMarkers(data)
    }
}