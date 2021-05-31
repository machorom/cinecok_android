package com.daou.cinecok.data.restapi;

import com.google.gson.annotations.SerializedName

data class KSearchTheaterResponse(
    @SerializedName("meta")
    val metaData: KTheaterMetaData,

    @SerializedName("documents")
    val listResult : List<KTheaterData>
)

data class KTheaterMetaData(
    @SerializedName("is_end")
    val isEnd: Boolean,

    @SerializedName("pageable_count")
    val maxPage: Int,

    @SerializedName("same_name")
    val detailInfo: KMetaDetailData,

    @SerializedName("total_count")
    val totalCnt: Int
)

data class KMetaDetailData(
    @SerializedName("keyword")
    val keyword: String,

    @SerializedName("region")
    val region: List<String>,

    @SerializedName("selected_region")
    val selRegion: String
)

data class KTheaterData (
    @SerializedName("address_name")
    val addrName: String,

    @SerializedName("category_group_code")
    val categoryGroupCode: String,

    @SerializedName("category_group_name")
    val categoryGroupName: String,

    @SerializedName("category_name")
    val categoryName: String,

    @SerializedName("distance")
    val distance: Int,

    @SerializedName("id")
    val id: Long,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("place_name")
    val placeName: String,

    @SerializedName("place_url")
    val placeUrl: String,

    @SerializedName("road_address_name")
    val roadAddrName: String,

    @SerializedName("x")
    val longitude: Double,

    @SerializedName("y")
    val latitude: Double
)
