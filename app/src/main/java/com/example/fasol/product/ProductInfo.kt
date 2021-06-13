package com.example.fasol.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fasol.ProfileManager
import com.example.fasol.R
import com.example.fasol.authorization.OrdersEmptyDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.orders_empty.*
import kotlinx.android.synthetic.main.product_info.*
import kotlinx.android.synthetic.main.profile.*

class ProductInfo: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!ProfileManager.isUserExist()){
            button_add.visibility = View.INVISIBLE
            product_info_condition.visibility = View.VISIBLE
        }else{
            product_info_condition.visibility = View.INVISIBLE
            button_add.visibility = View.VISIBLE
        }

    }
}