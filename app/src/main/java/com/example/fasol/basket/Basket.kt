package com.example.fasol.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.*
import com.example.fasol.Basket
import com.example.fasol.product.BasketAdapter
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Response

class Basket : Fragment() {
    private lateinit var recycle:RecyclerView
    private lateinit var total:TextView
    private lateinit var orderbtn:MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_basket, container, false)
        recycle = v.findViewById(R.id.basketitems_view)
        total = v.findViewById(R.id.totalPrice)
        orderbtn = v.findViewById(R.id.orderBtn)

        orderbtn.setOnClickListener {
            doOrder()
        }

        recycle.setHasFixedSize(true)
        recycle.layoutManager =
            GridLayoutManager(requireContext().applicationContext, 1)

        RetrofitClient.instance.getBasket("Bearer " + TokenManager.AccessToken).enqueue(object : retrofit2.Callback<Basket> {
            override fun onResponse(
                call: Call<Basket>,
                response: Response<Basket>
            ) {
                LoadProducts(response.body()!!.products as ArrayList<Products>)
                total.text = response.body()!!.totalPrice
            }

            override fun onFailure(
                call: Call<Basket>,
                t: Throwable
            ) {
                Toast.makeText(context, "Что-то пошло не так!", Toast.LENGTH_SHORT)
            }
        })

        return v
    }

    private fun doOrder()
    {
//        val user = ProfileManager.getCurrentUser()
//        RetrofitClient.instance.createOrder("Bearer " + TokenManager.AccessToken, CreateOrderModel(user.firstName,user.lastName, user.phone, user.address, ))
    }

    private fun LoadProducts(products:ArrayList<Products>)
    {
        recycle.adapter = BasketAdapter(products)
    }
}