package com.example.fasol.registration

import TokenManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fasol.*
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.sign_in.*
import kotlinx.serialization.ImplicitReflectionSerializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignIn : Fragment(R.layout.sign_in) {

    private lateinit var login: TextInputEditText
    private lateinit var password: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.sign_in, container, false)

        login = v.findViewById(R.id.number)
        password = v.findViewById(R.id.Password)

        return v
    }

    @ImplicitReflectionSerializer
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_sign_in.setOnClickListener {
            DoLogin()
        }

        button_register.setOnClickListener {
            val action = SignInDirections.actionSignInToRegistration()
            findNavController().navigate(action)
        }
    }

    @ImplicitReflectionSerializer
    private fun DoLogin() {
        RetrofitClient.instance.getToken(
            PhonePasswordModel(
                "8" + login.text.toString(),
                password.text.toString()
            )
        ).enqueue(object :
            Callback<TokenResponce> {

            override fun onResponse(call: Call<TokenResponce>, response: Response<TokenResponce>) {
                if (response.code() == 200) {
                    TokenManager.AccessToken = response.body()?.access!!
                    TokenManager.RefreshToken = response.body()?.refresh!!
                    GetUser()
                } else if (response.code() == 401) {
                    Toast.makeText(context, "Неверный логин или пароль!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        context,
                        "Что-то пошло не так! ${response.code()} ${response.message()}\"",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<TokenResponce>, t: Throwable) {
                Toast.makeText(context, "Что-то пошло не так!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun GetUser() {
        RetrofitClient.instance.getUser("Bearer " + TokenManager.AccessToken).enqueue(object :
            Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.code() == 200) {
                    context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                        ?.edit()?.putString("user", response.body()?.toJson())!!.apply()
                    val action = SignInDirections.actionSignInToProfileAuth()
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(
                        context,
                        "Что-то пошло не так! ${response.code()} ${response.message()}\"",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(context, "Что-то пошло не так!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}