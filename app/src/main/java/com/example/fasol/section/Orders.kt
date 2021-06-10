package com.example.fasol.section

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fasol.R
import com.example.fasol.authorization.OrdersEmptyDirections
import kotlinx.android.synthetic.main.orders_empty.*

class Orders : Fragment(R.layout.fragment_orders) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_category_open.setOnClickListener{
            val action = OrdersEmptyDirections.actionOrdersEmptyToMainNavigation()
            findNavController().navigate(action)
        }


    }
}