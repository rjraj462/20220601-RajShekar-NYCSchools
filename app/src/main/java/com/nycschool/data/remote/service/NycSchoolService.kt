package com.nycschool.data.remote.service

import com.nycschool.data.school.SchoolItem
import com.nycschool.data.school.SchoolSatItem
import retrofit2.Response
import retrofit2.http.GET

interface NycSchoolService {
    @GET("s3k6-pzi2.json")
    suspend fun fetchNYCSchoolList(): Response<List<SchoolItem>>

    @GET("f9bf-2cp4.json")
    suspend fun fetchNYCSchoolWithSatResult(): Response<List<SchoolSatItem>>
}
