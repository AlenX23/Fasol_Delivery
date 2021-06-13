package com.example.fasol.authorization

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fasol.ProfileManager
import com.example.fasol.R
import kotlinx.android.synthetic.main.orders_empty.*

class OrdersEmpty : Fragment(R.layout.orders_empty) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!ProfileManager.isUserExist())
            findNavController().navigate(R.id.profile_navigation)

        button_go_main.setOnClickListener {
            findNavController().navigate(R.id.main)
        }
    }
}