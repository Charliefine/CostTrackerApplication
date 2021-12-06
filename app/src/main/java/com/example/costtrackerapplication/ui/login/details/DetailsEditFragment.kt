package com.example.costtrackerapplication.ui.login.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.activities.DetailsActivity


class DetailsEditFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.details_edit_fragment, container, false)
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as DetailsActivity).supportActionBar?.title = "Edit expense"


    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_details_bar_edit).isVisible = false
        menu.findItem(R.id.menu_details_bar_delete).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

}