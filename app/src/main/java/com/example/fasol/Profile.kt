package com.example.fasol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fasol.registration.Authorization

class Profile: Fragment() {
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        communicator = this.activity as Communicator
        communicator.passData(R.id.fragment_profile, Authorization())

        return v
    }
}