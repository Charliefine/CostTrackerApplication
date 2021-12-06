package com.example.costtrackerapplication.ui.login.details

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.costtrackerapplication.activities.DetailsActivity
import com.example.costtrackerapplication.databinding.DetailsShowFragmentBinding


class DetailsShowFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private var _binding: DetailsShowFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemID: String

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

        val bundleFromActivity = this.arguments
        itemID = bundleFromActivity?.getString("itemID").toString()

        //ViewModel
        detailsViewModel = ViewModelProvider(this, MyDetailsViewModelFactory(itemID)).get(DetailsViewModel::class.java)

        //Listener to Item from ViewModel
        detailsViewModel.itemDetails.observe(viewLifecycleOwner, Observer {
            binding.test.text = it.title.toString()
        })

        (activity as DetailsActivity).supportActionBar?.title = "Expense details"

    }

    fun onFragmentInteraction(mItemID: String?) {
        itemID = mItemID!!
    }
}

