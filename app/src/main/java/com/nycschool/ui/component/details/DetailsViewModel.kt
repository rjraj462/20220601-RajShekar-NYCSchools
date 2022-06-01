package com.nycschool.ui.component.details

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nycschool.data.DataRepositorySource
import com.nycschool.data.Resource
import com.nycschool.data.school.SchoolItem
import com.nycschool.data.school.SchoolSatItem
import com.nycschool.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
open class DetailsViewModel @Inject constructor(private val dataRepository: DataRepositorySource) :
    BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val schoolItemPrivate = MutableLiveData<SchoolItem>()
    val schoolItem: LiveData<SchoolItem> get() = schoolItemPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val schoolSatItemPrivate = MutableLiveData<SchoolSatItem>()
    val schoolSatItem: LiveData<SchoolSatItem> get() = schoolSatItemPrivate

    fun initIntentData(school: SchoolItem, sat : SchoolSatItem) {
        schoolItemPrivate.value = school
        schoolSatItemPrivate.value = sat
    }
}
