package com.example.costtrackerapplication.ui.login.expense

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.ExpenseSummaryFragmentBinding
import com.example.costtrackerapplication.model.CategorySummaryAdapter


class ExpenseSummaryFragment : Fragment() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private var _binding: ExpenseSummaryFragmentBinding? = null
    private val binding get() = _binding!!

    private var categoriesKeys: ArrayList<String> = arrayListOf()
    private var categoriesValues: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Binding
        _binding = ExpenseSummaryFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ViewModel
        expenseViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        expenseViewModel.summaryExpense.observe(viewLifecycleOwner, {
            binding.expenseSummaryAmountInput.inputType = InputType.TYPE_NULL
            binding.expenseSummaryAmountLayout.hint = "$it PLN"
        })

        //Create list
        val fragmentManager: FragmentManager? = activity?.supportFragmentManager
        val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        val transactionAnimation = fragmentTransaction?.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
        transactionAnimation?.replace(R.id.expense_summary_frame_layout, ExpenseListFragment())?.commit()

        //TODO Zmienić adapter z VM do widoku
        expenseViewModel.categoriesExpense.observe(viewLifecycleOwner, { it ->
            categoriesKeys.clear()
            categoriesValues.clear()
            it.forEach {
                var categoryName = it.key
                categoryName = categoryName.replace("[", "").replace("]", "")
                categoriesKeys.add(categoryName)
                categoriesValues.add(it.value.toString())
            }

            val listAdapter = CategorySummaryAdapter(requireActivity(), categoriesKeys, categoriesValues)
            binding.expenseSummaryListview.adapter = listAdapter

            binding.expenseSummaryListview.setOnItemClickListener { parent, _, position, _ ->

                expenseViewModel.setCategory(parent.adapter.getItem(position).toString())

                //Create list
                val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
                val transactionAnimation = fragmentTransaction?.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
                transactionAnimation?.replace(R.id.expense_summary_frame_layout, ExpenseListFragment())?.commit()
            }
        })

        binding.expenseSummaryAmountInput.setOnClickListener {
            expenseViewModel.setCategory("")

            //Create list
            val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
            val transactionAnimation = fragmentTransaction?.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
            transactionAnimation?.replace(R.id.expense_summary_frame_layout, ExpenseListFragment())?.commit()
        }
/*
        binding.expenseSummaryClearFilters.setOnClickListener {
            expenseViewModel.setCategory("")

            //Create list
            val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
            val transactionAnimation = fragmentTransaction?.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
            transactionAnimation?.replace(R.id.expense_summary_frame_layout, ExpenseListFragment())?.commit()
        }*/
    }
}