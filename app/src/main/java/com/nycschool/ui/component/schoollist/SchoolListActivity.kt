package com.nycschool.ui.component.schoollist

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nycschool.R
import com.nycschool.SCHOOL_ITEM_KEY
import com.nycschool.SCHOOL_SAT_ITEM_KEY
import com.nycschool.data.Resource
import com.nycschool.data.school.NYCschool
import com.nycschool.data.school.NYCschoolSat
import com.nycschool.data.school.SchoolItem
import com.nycschool.data.school.SchoolSatItem
import com.nycschool.databinding.HomeActivityBinding
import com.nycschool.ui.base.BaseActivity
import com.nycschool.ui.component.details.DetailsActivity
import com.nycschool.ui.component.schoollist.adapter.SchoolListAdapter
import com.nycschool.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SchoolListActivity : BaseActivity() {
    private lateinit var binding: HomeActivityBinding

    private val schoolListViewModel: SchoolListViewModel by viewModels()
    private lateinit var schoolListAdapter: SchoolListAdapter

    override fun initViewBinding() {
        binding = HomeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.title_activity_list)
        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager
        binding.rvList.setHasFixedSize(true)
        schoolListViewModel.getSchoolList()
        schoolListViewModel.getSchoolListWith()
    }

    private fun bindListData(NYCschool: NYCschool) {
        if (NYCschool.schoolList.isNotEmpty()) {
            schoolListAdapter = SchoolListAdapter(schoolListViewModel, NYCschool.schoolList)
            binding.rvList.adapter = schoolListAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun navigateToDetailsScreen(navigateEvent: SingleEvent<Pair<SchoolItem, SchoolSatItem>>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val nextScreenIntent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(SCHOOL_ITEM_KEY, it.first)
                putExtra(SCHOOL_SAT_ITEM_KEY, it.second)
            }
            startActivity(nextScreenIntent)
        }
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) GONE else VISIBLE
        binding.rvList.visibility = if (show) VISIBLE else GONE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvList.toGone()
    }

    private fun handleSchoolsList(status: Resource<NYCschool>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(NYCschool = it) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { schoolListViewModel.showToastMessage(it) }
            }
        }
    }

    private fun handleSchoolsSatList(status: Resource<NYCschoolSat>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            // is Resource.Success -> // no-op
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { schoolListViewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(schoolListViewModel.nycSchoolLiveData, ::handleSchoolsList)
        observe(schoolListViewModel.nycSchoolSatLiveData, ::handleSchoolsSatList)
        observeEvent(schoolListViewModel.openDetails, ::navigateToDetailsScreen)
        observeSnackBarMessages(schoolListViewModel.showSnackBar)
        observeToast(schoolListViewModel.showToast)

    }
}
