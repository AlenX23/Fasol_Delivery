package com.example.fasol.authorization

import TokenManager
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fasol.*
import kotlinx.serialization.ImplicitReflectionSerializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileAuth : Fragment(R.layout.profile) {
    lateinit var name: TextView
    lateinit var phone: TextView
    lateinit var signOut: Button
    lateinit var updateToken: Button

    @SuppressLint("SetTextI18n")
    @ImplicitReflectionSerializer
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.profile, container, false)


        name = v.findViewById(R.id.name)
        phone = v.findViewById(R.id.number)
        signOut = v.findViewById(R.id.signOut)
        updateToken = v.findViewById(R.id.updateToken)

        name.text =
            ProfileManager.getCurrentUser().firstName + " " + ProfileManager.getCurrentUser().lastName
        phone.text = ProfileManager.getCurrentUser().phone

        signOut.setOnClickListener {
            ProfileManager.Clear()
            findNavController().navigate(R.id.login)
        }

        updateToken.setOnClickListener {
            RetrofitClient.instance.updateAccessToken(RefreshModel(TokenManager.RefreshToken))
                .enqueue(object :
                    Callback<TokenResponce> {

                    override fun onResponse(
                        call: Call<TokenResponce>,
                        response: Response<TokenResponce>
                    ) {
                        if (response.code() == 200) {
                            TokenManager.AccessToken = response.body()?.access!!
                            TokenManager.RefreshToken = response.body()?.refresh!!
                            Toast.makeText(context, "Токен успешно обновлен!", Toast.LENGTH_SHORT)
                                .show()
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
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}