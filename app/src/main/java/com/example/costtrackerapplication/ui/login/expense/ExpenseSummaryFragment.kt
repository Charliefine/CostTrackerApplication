package com.example.costtrackerapplication.ui.login.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.ExpenseSummaryFragmentBinding
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry





class ExpenseSummaryFragment : Fragment() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private var _binding: ExpenseSummaryFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Binding
        _binding = ExpenseSummaryFragmentBinding.inflate(inflater, container, false)

        //ViewModel
        expenseViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chart = view.findViewById(R.id.categories_chart) as HorizontalBarChart
        val data = BarData(getDataSet())
        chart.data = data
        chart.animateXY(2000, 2000)
        chart.setDrawValueAboveBar(true);
        chart.invalidate()

        chart.xAxis.setDrawGridLines(false);
        chart.axisLeft.setDrawGridLines(false);
    }
    private fun getDataSet(): BarDataSet? {
        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(4f, 0f))
        entries.add(BarEntry(8f, 1f))
        entries.add(BarEntry(6f, 2f))
        entries.add(BarEntry(12f, 3f))
        entries.add(BarEntry(18f, 4f))
        entries.add(BarEntry(9f, 5f))
        return BarDataSet(entries, "hi")
    }
    private fun getXAxisValues(): ArrayList<String>? {
        val labels: ArrayList<String> = ArrayList()
        labels.add("January")
        labels.add("February")
        labels.add("March")
        labels.add("April")
        labels.add("May")
        labels.add("June")
        return labels
    }
}