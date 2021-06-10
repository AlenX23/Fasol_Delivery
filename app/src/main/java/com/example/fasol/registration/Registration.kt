package com.example.fasol.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fasol.R
import kotlinx.android.synthetic.main.registration.*

class Registration : Fragment(R.layout.registration) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_done.setOnClickListener {
            val action = RegistrationDirections.actionRegistrationToProfileAuth()
            findNavController().navigate(action)
        }
    }
}