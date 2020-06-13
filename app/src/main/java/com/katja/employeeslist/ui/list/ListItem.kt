package com.katja.employeeslist.ui.list

import com.katja.employeeslist.R
import com.katja.employeeslist.data.db.entity.Employee
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.list_item.*

class ListItem(val employee: Employee) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            tvName.text = employee.name
        }
    }

    override fun getLayout() = R.layout.list_item
}