package com.katja.employeeslist.ui.fragments.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.katja.employeeslist.R
import com.katja.employeeslist.data.db.entity.Employee
import com.katja.employeeslist.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.analytics_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate
import org.threeten.bp.Period

class AnalyticsFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModelFactory by instance<AnalyticsViewModelFactory>()

    private lateinit var viewModel: AnalyticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.analytics_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AnalyticsViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val employeeList = viewModel.employees.await()
        employeeList.observe(viewLifecycleOwner, Observer { result ->
            if (result == null) return@Observer

            initAgeChart(result.getAgeList())
            initSalaryChart(result.getSalaryList())
            initGenderChart(result.groupByGender().toDataEntryList())
        })
    }

    private fun initAgeChart(data: List<Int>) {
        val averageAge = getAverageAge(data)
        val medianAge = getMedianAge(data)
        val dataReady = data.toDataEntryList()
        APIlib.getInstance().setActiveAnyChartView(anyChartViewAge)
        val chart = AnyChart.column()
        chart.data(dataReady)
        chart.title("AGE (average = $averageAge, median = $medianAge)")
        anyChartViewAge.setChart(chart)
    }

    private fun initSalaryChart(data: List<Double>) {
        val maxSalary = data.max()
        val dataReady = data.toDataEntryList()
        APIlib.getInstance().setActiveAnyChartView(anyChartViewSalary)
        val chart = AnyChart.line()
        chart.data(dataReady)
        chart.title("SALARY (max = $maxSalary)")
        anyChartViewSalary.setChart(chart)
    }

    private fun initGenderChart(data: List<DataEntry>) {
        APIlib.getInstance().setActiveAnyChartView(anyChartViewGender)
        val chart = AnyChart.pie()
        chart.data(data)
        chart.title("GENDER")
        anyChartViewGender.setChart(chart)
    }

    private fun <K, V> Map<K, V>.toDataEntryList(): List<DataEntry> {
        return this.entries.map {
            ValueDataEntry(it.key.toString(), it.value.toString().toInt())
        }
    }

    private fun <T> List<T>.toDataEntryList(): List<DataEntry> {
        return this.mapIndexed { index, i ->
            ValueDataEntry(index, i.toString().toDouble())
        }
    }

    private fun List<Employee>.getAgeList(): List<Int> {
        return this.map {
            it.birthday.convertToAge()
        }
    }

    private fun String.convertToAge(): Int {
        val date = this.split("/")
        return getAge(date[0].toInt(), date[1].toInt(), date[2].toInt())
    }

    private fun getAge(day: Int, month: Int, year: Int): Int {
        val birthday = LocalDate.of(year, month, day)
        val today = LocalDate.now()
        return Period.between(birthday, today).years
    }

    private fun getAverageAge(ageList: List<Int>): Double {
        return ageList.average()
    }

    private fun getMedianAge(ageList: List<Int>): Double {
        val data = ageList.map { it.toDouble() }
        return data.sorted().let { (it[it.size / 2] + it[(it.size - 1) / 2]) / 2 }
    }

    private fun List<Employee>.getSalaryList(): List<Double> {
        return this.map {
            it.salary
        }
    }

    private fun List<Employee>.groupByGender(): Map<String, Int> {
        return this.groupingBy { it.gender }.eachCount()
    }
}