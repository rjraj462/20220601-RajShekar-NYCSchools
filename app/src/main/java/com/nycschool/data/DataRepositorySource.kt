package com.nycschool.data

import com.nycschool.data.school.NYCschool
import com.nycschool.data.school.NYCschoolSat
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun fetchNYCSchoolList(): Flow<Resource<NYCschool>>
    suspend fun fetchNYCSchoolWithSatResult(): Flow<Resource<NYCschoolSat>>
}
