package com.nycschool.ui.component.schoollist

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nycschool.data.DataRepositorySource
import com.nycschool.data.Resource
import com.nycschool.data.school.NYCschool
import com.nycschool.data.school.NYCschoolSat
import com.nycschool.data.school.SchoolItem
import com.nycschool.data.school.SchoolSatItem
import com.nycschool.ui.base.BaseViewModel
import com.nycschool.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SchoolListViewModel @Inject
constructor(private val dataRepositoryRepository: DataRepositorySource) : BaseViewModel() {

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val nycSchoolLiveDataPrivate = MutableLiveData<Resource<NYCschool>>()
    val nycSchoolLiveData: LiveData<Resource<NYCschool>> get() = nycSchoolLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val nycSchoolSatLiveDataPrivate = MutableLiveData<Resource<NYCschoolSat>>()
    val nycSchoolSatLiveData: LiveData<Resource<NYCschoolSat>> get() = nycSchoolSatLiveDataPrivate

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val openDetailsPrivate = MutableLiveData<SingleEvent<Pair<SchoolItem,SchoolSatItem>>>()
    val openDetails: LiveData<SingleEvent<Pair<SchoolItem,SchoolSatItem>>> get() = openDetailsPrivate

    /**
     * Error handling as UI
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate


    fun getSchoolList() {
        viewModelScope.launch {
            nycSchoolLiveDataPrivate.value = Resource.Loading()
            dataRepositoryRepository.fetchNYCSchoolList().collect {
                nycSchoolLiveDataPrivate.value = it
            }
        }
    }

    fun getSchoolListWith() {
        viewModelScope.launch {
            nycSchoolLiveDataPrivate.value = Resource.Loading()
            dataRepositoryRepository.fetchNYCSchoolWithSatResult().collect {
                nycSchoolSatLiveDataPrivate.value = it
            }
        }
    }

    fun openSchoolDetails(schoolItem: SchoolItem) {
        var schoolSatItem: SchoolSatItem? = null
        val list: ArrayList<SchoolSatItem>? = nycSchoolSatLiveDataPrivate.value?.data?.schoolList
        schoolSatItem = list?.find {
            Log.e("!_@_","${it.dbn} --- ${schoolItem.dbn}")
            it.dbn == schoolItem.dbn
        }
        if (schoolSatItem == null) {
            showToastPrivate.value = SingleEvent("No SAT Score found for ${schoolItem.school_name}")
        } else {
            openDetailsPrivate.value = SingleEvent(Pair(schoolItem,schoolSatItem))
        }
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }
}
