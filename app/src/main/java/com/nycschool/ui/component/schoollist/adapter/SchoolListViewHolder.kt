package com.nycschool.ui.component.schoollist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.nycschool.data.school.SchoolItem
import com.nycschool.databinding.SchoolItemBinding
import com.nycschool.ui.base.listeners.RecyclerItemListener


class SchoolListViewHolder(private val itemBinding: SchoolItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(schoolItem: SchoolItem, recyclerItemListener: RecyclerItemListener) {
        itemBinding.tvName.text = schoolItem.school_name
        itemBinding.tvPhone.text = schoolItem.phone_number
        itemBinding.tvEmail.text = schoolItem.school_email
        itemBinding.rlItem.setOnClickListener {
            recyclerItemListener.onItemSelected(
                schoolItem
            )
        }
    }
}

