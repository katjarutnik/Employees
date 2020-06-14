package com.katja.employeeslist.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.katja.employeeslist.R
import com.katja.employeeslist.internal.EmployeeIdNotFoundException
import com.katja.employeeslist.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory

class ProfileFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModelFactoryInstanceFactory: ((Int) -> ProfileViewModelFactory) by factory<Int, ProfileViewModelFactory>()

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { ProfileFragmentArgs.fromBundle(it) }
        val employeeId = safeArgs?.employeeId ?: throw EmployeeIdNotFoundException()

        viewModel = ViewModelProvider(this, viewModelFactoryInstanceFactory(employeeId)).get(
            ProfileViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val employee = viewModel.employee.await()

        employee.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            textViewName.text = it.name
            textViewBirthday.text = it.birthday
            textViewGender.text = it.gender
            textViewSalary.text = it.salary.toString()
        })
    }

}