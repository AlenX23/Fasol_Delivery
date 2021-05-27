package com.example.fasol.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.fasol.Communicator
import com.example.fasol.R

class Login : Fragment() {
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.login_button, container, false)

        val button: Button = v.findViewById(R.id.login)
        button.setOnClickListener {
            communicator = this.activity as Communicator
            communicator.passData(R.id.authorizationFragment, RegistrationFirst())
        }
        return v
    }
}