package com.nycschool.data.school


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class SchoolSatItem(
    @Json(name = "dbn")
    val dbn: String? = null,
    @Json(name = "num_of_sat_test_takers")
    val num_of_sat_test_takers: String? = null,
    @Json(name = "sat_critical_reading_avg_score")
    val sat_critical_reading_avg_score: String? = null,
    @Json(name = "sat_math_avg_score")
    val sat_math_avg_score: String? = null,
    @Json(name = "sat_writing_avg_score")
    val sat_writing_avg_score: String? = null
) : Parcelable
