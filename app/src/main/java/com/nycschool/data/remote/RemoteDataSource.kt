package com.nycschool.data.remote

import com.nycschool.data.Resource
import com.nycschool.data.school.NYCschool
import com.nycschool.data.school.NYCschoolSat

internal interface RemoteDataSource {
    suspend fun fetchNYCSchoolList(): Resource<NYCschool>
    suspend fun fetchNYCSchoolWithSatResult(): Resource<NYCschoolSat>
}
