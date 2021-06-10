package com.example.fasol.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fasol.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.registration.*
import kotlinx.android.synthetic.main.registration.textView
import kotlinx.android.synthetic.main.sign_in.*

class Login : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_login.setOnClickListener {
            val action = LoginDirections.actionLoginToSignIn()
            findNavController().navigate(action)
        }
    }
}