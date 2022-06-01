package com.nycschool.ui.component.schoollist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nycschool.data.school.SchoolItem
import com.nycschool.databinding.SchoolItemBinding
import com.nycschool.ui.base.listeners.RecyclerItemListener
import com.nycschool.ui.component.schoollist.SchoolListViewModel


class SchoolListAdapter(
    private val schoolListViewModel: SchoolListViewModel,
    private val schoolItem: List<SchoolItem>
) : RecyclerView.Adapter<SchoolListViewHolder>() {

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(recipe: SchoolItem) {
            schoolListViewModel.openSchoolDetails(recipe)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolListViewHolder {
        val itemBinding =
            SchoolItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SchoolListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SchoolListViewHolder, position: Int) {
        holder.bind(schoolItem[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return schoolItem.size
    }
}

