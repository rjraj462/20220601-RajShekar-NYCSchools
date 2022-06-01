package com.nycschool.data

import com.nycschool.data.school.NYCschool
import com.nycschool.data.remote.RemoteData
import com.nycschool.data.school.NYCschoolSat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(
    private val remoteRepository: RemoteData,
    private val ioDispatcher: CoroutineContext
) : DataRepositorySource {

    override suspend fun fetchNYCSchoolList(): Flow<Resource<NYCschool>> {
        return flow {
            emit(remoteRepository.fetchNYCSchoolList())
        }.flowOn(ioDispatcher)
    }

    override suspend fun fetchNYCSchoolWithSatResult(): Flow<Resource<NYCschoolSat>> {
        return flow {
            emit(remoteRepository.fetchNYCSchoolWithSatResult())
        }.flowOn(ioDispatcher)
    }
}
