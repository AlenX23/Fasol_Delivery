package com.example.fasol.authorization

import TokenManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fasol.*
import kotlinx.android.synthetic.main.orders_empty.*
import retrofit2.Call
import retrofit2.Response

class OrdersEmpty : Fragment(R.layout.orders_empty) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.orders_empty, container, false)

        if (ProfileManager.isUserExist())
            CheckOrdersExist()

        return v
    }

    private fun CheckOrdersExist() {
        RetrofitClient.instance.getOrders("Bearer " + TokenManager.AccessToken).enqueue(object :
            retrofit2.Callback<List<OrderModel>> {

            override fun onResponse(
                call: Call<List<OrderModel>>,
                response: Response<List<OrderModel>>
            ) {
                if (response.code() == 200) {
                    if (response.body()!!.isNotEmpty()) {
                        findNavController().navigate(R.id.orders_list)
                    }
                }
            }

            override fun onFailure(call: Call<List<OrderModel>>, t: Throwable) {
                Toast.makeText(context, "Что-то пошло не так!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!ProfileManager.isUserExist())
            findNavController().navigate(R.id.profile_navigation)

        button_go_main.setOnClickListener {
            findNavController().navigate(R.id.main_navigation)
        }
    }
}