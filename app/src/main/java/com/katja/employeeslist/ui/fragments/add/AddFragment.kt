package com.katja.employeeslist.ui.fragments.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.katja.employeeslist.R
import com.katja.employeeslist.data.db.entity.Employee
import com.katja.employeeslist.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.add_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate

class AddFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModelFactory by instance<AddViewModelFactory>()

    private lateinit var viewModel : AddViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        buttonGender.setOnClickListener { showDialogGenderPicker() }

        buttonAdd.setOnClickListener { addEmployee() }
    }

    private fun showDialogGenderPicker() {
        val listItems = resources.getStringArray(R.array.employee_gender)
        val mBuilder = AlertDialog.Builder(requireContext())
        mBuilder.setTitle("Employee gender")
        mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->
            buttonGender.text = listItems[i]
            dialogInterface.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun addEmployee() {
        if (valuesValid()) {
            val firstName = editTextFristName.text.toString()
            val lastName = editTextLastName.text.toString()
            val birthday = "${datePickerBirthday.dayOfMonth}/${datePickerBirthday.month + 1}/${datePickerBirthday.year}"
            val gender = buttonGender.text.toString()
            val salary = editTextSalary.text.toString().toDouble()

            val employee = Employee(employeeId = 0, name = "$firstName $lastName",
                birthday = birthday, gender = gender, salary = salary)

            addEmployeeToDatabase(employee)

            showEmployeesListFragment(requireView())
        } else {
            textViewError.visibility = View.VISIBLE
        }
    }

    private fun valuesValid() : Boolean {
        return when {
            datePickerBirthday.year >= LocalDate.now().year -> false
            editTextFristName.text.trim().isEmpty() -> false
            editTextLastName.text.trim().isEmpty() -> false
            buttonGender.text.isEmpty() -> false
            else -> editTextSalary.text.trim().isNotEmpty()
        }
    }

    private fun addEmployeeToDatabase(employee: Employee) = launch(Dispatchers.Main) {
        viewModel.addEmployee(employee)
    }

    private fun showEmployeesListFragment(view: View) {
        val action = AddFragmentDirections.actionAddFragmentToListFragment()
        Navigation.findNavController(view).navigate(action)
    }

}