package com.example.fasol.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.example.fasol.R
import kotlinx.android.synthetic.main.registration.*
import kotlinx.android.synthetic.main.sign_in.*

class SignIn : Fragment(R.layout.sign_in) {

    //private val args: SignInArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_sign_in.setOnClickListener{
            val action = SignInDirections.actionSignInToProfileAuth()
            findNavController().navigate(action)
        }

        button_register.setOnClickListener {
            val action = SignInDirections.actionSignInToRegistration()
            findNavController().navigate(action)
        }


    }
}