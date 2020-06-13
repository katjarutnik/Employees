package com.katja.employeeslist.ui.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.katja.employeeslist.R
import com.katja.employeeslist.data.db.entity.Employee
import com.katja.employeeslist.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModelFactory by instance<ListViewModelFactory>()

    private lateinit var viewModel : ListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val employeeList = viewModel.employeeList.await()
        employeeList.observe(viewLifecycleOwner, Observer { result ->
            if (result == null) return@Observer

            initRecyclerView(result.toListItems())

            group_loading.visibility = View.GONE
        })
    }

    private fun initRecyclerView(items : List<ListItem>) {
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = groupAdapter
        }
    }

    private fun List<Employee>.toListItems() : List<ListItem> {
        return this.map {
            ListItem(it)
        }
    }
}