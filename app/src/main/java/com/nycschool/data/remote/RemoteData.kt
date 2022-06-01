package com.nycschool.data.remote

import com.nycschool.data.Resource
import com.nycschool.data.school.NYCschool
import com.nycschool.data.school.SchoolItem
import com.nycschool.data.error.NETWORK_ERROR
import com.nycschool.data.error.NO_INTERNET_CONNECTION
import com.nycschool.data.remote.service.NycSchoolService
import com.nycschool.data.school.NYCschoolSat
import com.nycschool.data.school.SchoolSatItem
import com.nycschool.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteData @Inject
constructor(
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity
) : RemoteDataSource {
    override suspend fun fetchNYCSchoolList(): Resource<NYCschool> {
        val schoolService = serviceGenerator.createService(NycSchoolService::class.java)
        return when (val response = processCall(schoolService::fetchNYCSchoolList)) {
            is List<*> -> {
                Resource.Success(data = NYCschool(response as ArrayList<SchoolItem>))
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }
    override suspend fun fetchNYCSchoolWithSatResult(): Resource<NYCschoolSat> {
        val schoolService = serviceGenerator.createService(NycSchoolService::class.java)
        return when (val response = processCall(schoolService::fetchNYCSchoolWithSatResult)) {
            is List<*> -> {
                Resource.Success(data = NYCschoolSat(response as ArrayList<SchoolSatItem>))
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}
