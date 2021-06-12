package com.example.fasol.registration

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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.registration.*
import kotlinx.serialization.ImplicitReflectionSerializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registration : Fragment(R.layout.registration) {

    private lateinit var tbLogin: TextInputEditText
    private lateinit var tbPassword: TextInputEditText
    private lateinit var tbUserName: TextInputEditText
    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.registration, container, false)

        return v
    }

    @kotlinx.serialization.ImplicitReflectionSerializer
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tbLogin = v.findViewById(R.id.number)
        tbPassword = v.findViewById(R.id.Password)
        tbUserName = v.findViewById(R.id.userName)


        button_done.setOnClickListener {
            if(tbLogin.text.toString() == "" || tbPassword.text.toString() == "" || tbUserName.text.toString() == "")
            {
                Toast.makeText(context, "Заполните все поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            RetrofitClient.instance.registerUser(PhonePasswordModel("8" + tbLogin.text.toString(), tbPassword.text.toString())).enqueue(object :
                Callback<RegisterUserResponce> {

                override fun onResponse(
                    call: Call<RegisterUserResponce>,
                    response: Response<RegisterUserResponce>
                ) {
                    if(response.code() == 201)
                    {
                        GetToken()
                    }
                    else if(response.code() == 400)
                    {
                        Toast.makeText(context, "Аккаунт с таким номером уже существует!", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(context, "Что-то пошло не так! ${response.code()} ${response.message()}\"", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RegisterUserResponce>, t: Throwable) {
                    Toast.makeText(context, "Что-то пошло не так!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    @ImplicitReflectionSerializer
    private fun GetToken() {
        RetrofitClient.instance.getToken(PhonePasswordModel("8" + tbLogin.text.toString(), tbPassword.text.toString())).enqueue(object :
            Callback<TokenResponce> {

            override fun onResponse(call: Call<TokenResponce>, response: Response<TokenResponce>) {
                if(response.code() == 200)
                {
                    TokenManager.AccessToken = response.body()?.access!!
                    TokenManager.RefreshToken = response.body()?.refresh!!
                    ChangeUsername()
                }
                else
                {
                    Toast.makeText(context, "Что-то пошло не так! ${response.code()} ${response.message()}\"", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TokenResponce>, t: Throwable) {
                Toast.makeText(context, "Что-то пошло не так!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @ImplicitReflectionSerializer
    private fun ChangeUsername()
    {
        val fullName = tbUserName.text.toString().split(" ")
        val fname = fullName[0]
        var lname = ""
        if(fullName.size > 1)
            lname =  tbUserName.text.toString().split(" ")[1]
        val user = UserWithoutId("8" + tbLogin.text.toString(), fname, lname, "")

        RetrofitClient.instance.changeUsername(user, "Bearer " + TokenManager.AccessToken).enqueue(object :
            Callback<User>
        {

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.code() == 200)
                {
                    context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                        ?.edit()?.putString("user", response.body()?.toJson())!!.apply()

                    val bundle = Bundle()
                    bundle.putString("Username", "$fname $lname")
                    bundle.putString("Phone", tbLogin.text.toString())
                    //val action = RegistrationDirections.actionRegistrationToProfileAuth()
                    findNavController().navigate(R.id.profileAuth, bundle)
                }
                else
                {
                    Toast.makeText(context, "Что-то пошло не так! ${response.code()} ${response.message()}\"", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(context, "Что-то пошло не так!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
