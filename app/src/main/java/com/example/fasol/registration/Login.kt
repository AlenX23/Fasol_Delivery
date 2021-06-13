package com.example.fasol.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fasol.ProfileManager
import com.example.fasol.R
import kotlinx.android.synthetic.main.fragment_login.*

class Login : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ProfileManager.isUserExist()) {
            findNavController().navigate(R.id.profileAuth)
        }


        button_login.setOnClickListener {
            findNavController().navigate(R.id.signIn)
        }
    }
}