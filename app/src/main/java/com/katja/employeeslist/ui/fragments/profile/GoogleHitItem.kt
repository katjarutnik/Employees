package com.katja.employeeslist.ui.fragments.profile

import com.katja.employeeslist.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.google_hit_item.*

class GoogleHitItem(val hit: String): Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            textView_google_hit.text = hit
        }
    }

    override fun getLayout() = R.layout.google_hit_item

}