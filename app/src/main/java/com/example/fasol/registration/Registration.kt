package com.example.fasol.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavAction
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fasol.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.registration.*

class Registration : Fragment(R.layout.registration) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        button_done.setOnClickListener {


            val action = RegistrationDirections.actionRegistrationToProfileAuth()
            findNavController().navigate(action)
        }
    }
}