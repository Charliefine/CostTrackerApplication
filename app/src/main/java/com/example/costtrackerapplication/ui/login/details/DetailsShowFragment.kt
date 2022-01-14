package com.example.costtrackerapplication.ui.login.details

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.costtrackerapplication.activities.DetailsActivity
import com.example.costtrackerapplication.databinding.DetailsShowFragmentBinding


class DetailsShowFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private var _binding: DetailsShowFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Binding
        _binding = DetailsShowFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemID: String
        val bundleFromActivity = this.arguments
        itemID = bundleFromActivity?.getString("itemID").toString()

        //ViewModel
        detailsViewModel = ViewModelProvider(this, MyDetailsViewModelFactory(itemID)).get(DetailsViewModel::class.java)

        //Listener to Item from ViewModel
        detailsViewModel.itemDetails.observe(viewLifecycleOwner, {
            binding.detailsShowTitle.text = SpannableStringBuilder(it.title.toString())
            binding.detailsShowCategory.text = it.category.toString()
            binding.detailsShowDate.text = it.date.toString()
            binding.detailsShowDateAdded.text = it.addedDate.toString()
            binding.detailsShowDescription.text = it.description.toString()
            binding.detailsShowAmount.text = it.amount.toString() + " PLN"
        })

        (activity as DetailsActivity).supportActionBar?.title = "Expense details"
    }
}

