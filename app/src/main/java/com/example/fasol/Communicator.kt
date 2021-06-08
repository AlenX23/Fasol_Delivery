package com.example.fasol

import androidx.fragment.app.Fragment

interface Communicator {
    fun passData(container: Int, fragment: Fragment) {}
}

