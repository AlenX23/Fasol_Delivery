package com.example.fasol.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fasol.Communicator
import com.example.fasol.R

class Authorization : Fragment() {
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_authorization, container, false)

        communicator = this.activity as Communicator
        communicator.passData(R.id.authorizationFragment, Login())


        return v
    }
}