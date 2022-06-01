package com.nycschool.data.school


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class SchoolItem(
    @Json(name = "dbn")
    val dbn: String? = null,
    @Json(name = "school_name")
    val school_name: String? = null,
    @Json(name = "school_email")
    val school_email: String? = null,
    @Json(name = "building_code")
    val building_code: String? = null,
    @Json(name = "website")
    val website: String? = null,
    @Json(name = "phone_number")
    val phone_number: String? = null,
    @Json(name = "city")
    val city: String? = null,
    @Json(name = "zip")
    val zip: String? = null,
    @Json(name = "state_code")
    val state_code: String? = null,
    @Json(name = "latitude")
    val latitude: String? = null,
    @Json(name = "longitude")
    val longitude: String? = null,
) : Parcelable
