package com.example.fasol.section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fasol.ProfileManager
import com.example.fasol.R
import com.example.fasol.authorization.OrdersEmptyDirections
import kotlinx.android.synthetic.main.orders_empty.*

class Orders : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if(ProfileManager.isUserExist())
        {
            // Тут почему то крашит на фрагменте с заказами, хз почему
            findNavController().navigate(R.id.profileAuth)
        }

        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_category_open.setOnClickListener {
            val action = OrdersEmptyDirections.actionOrdersEmptyToMainNavigation()
            findNavController().navigate(action)
        }
    }

}