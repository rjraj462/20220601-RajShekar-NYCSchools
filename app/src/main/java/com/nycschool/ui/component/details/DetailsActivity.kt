package com.nycschool.ui.component.details

import android.os.Bundle
import com.nycschool.R
import com.nycschool.SCHOOL_ITEM_KEY
import com.nycschool.SCHOOL_SAT_ITEM_KEY
import com.nycschool.data.school.SchoolItem
import com.nycschool.data.school.SchoolSatItem
import com.nycschool.databinding.DetailsLayoutBinding
import com.nycschool.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsActivity : BaseActivity() {
    private lateinit var binding: DetailsLayoutBinding

    override fun initViewBinding() {
        binding = DetailsLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeView(
            intent.getParcelableExtra(SCHOOL_ITEM_KEY) ?: SchoolItem(),
            intent.getParcelableExtra(SCHOOL_SAT_ITEM_KEY) ?: SchoolSatItem()
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun observeViewModel() {
        //no-op
    }

    private fun initializeView(schoolItem: SchoolItem, schoolSatItem: SchoolSatItem) {
        binding.tvName.text = schoolItem.school_name
        binding.tvReading.text =
            getString(R.string.reading, schoolSatItem.sat_critical_reading_avg_score)
        binding.tvWriting.text = getString(R.string.writing, schoolSatItem.sat_writing_avg_score)
        binding.tvMath.text = getString(R.string.maths, schoolSatItem.sat_math_avg_score)
    }
}
