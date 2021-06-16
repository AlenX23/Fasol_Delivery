package com.example.fasol.basket

import TokenManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.Basket
import com.example.fasol.Products
import com.example.fasol.R
import com.example.fasol.RetrofitClient
import com.example.fasol.product.BasketAdapter
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Response

class Basket : Fragment() {
    private lateinit var recycle: RecyclerView
    private lateinit var total: TextView
    private lateinit var orderbtn: MaterialButton
    private lateinit var clearBasket: ImageButton
    private lateinit var closeBasket: ImageButton
    private lateinit var products: ArrayList<Products>
    private var basketId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_basket, container, false)
        recycle = v.findViewById(R.id.basketitems_view)
        total = v.findViewById(R.id.totalPrice)
        orderbtn = v.findViewById(R.id.orderBtn)
        clearBasket = v.findViewById(R.id.clear_basket_btn)
        closeBasket = v.findViewById(R.id.close_basket_btn)

        closeBasket.setOnClickListener {
            findNavController().popBackStack()
        }

        clearBasket.setOnClickListener {
            callAlertDialog()
        }

        orderbtn.setOnClickListener {
            doOrder()
        }

        recycle.setHasFixedSize(true)
        recycle.layoutManager =
            GridLayoutManager(requireContext().applicationContext, 1)

        RetrofitClient.instance.getBasket("Bearer " + TokenManager.AccessToken)
            .enqueue(object : retrofit2.Callback<Basket> {
                override fun onResponse(
                    call: Call<Basket>,
                    response: Response<Basket>
                ) {
                    if (response.code() == 200 && response.body()!!.id != 0) {
                        basketId = response.body()!!.id
                        products = response.body()!!.products as ArrayList<Products>
                        LoadProducts(response.body()!!.products as ArrayList<Products>)
                        total.text = response.body()!!.totalPrice
                    }
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

    private fun callAlertDialog() {
        if (recycle.adapter == null || recycle.adapter!!.itemCount == 0) {
            Toast.makeText(
                context,
                "Корзина пуста! Пожалуйста, добавьте товар в корзину!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Вы уверены, что хотите очистить корзину?")
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ ->
                doClearBasket()
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.colorPrimary))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.colorPrimary))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).text = "Да"
        }
        dialog.show()
    }

    private fun doClearBasket() {

        products.forEach {
            RetrofitClient.instance.deleteFromBasket(
                "Bearer " + TokenManager.AccessToken,
                it.id.toLong()
            ).enqueue(object : retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                }
            })
        }

        (recycle.adapter as BasketAdapter).clearItems()
        total.text = "0.00"
    }

    private fun doOrder() {
        if (recycle.adapter == null || recycle.adapter!!.itemCount == 0) {
            Toast.makeText(
                context,
                "Корзина пуста! Пожалуйста, добавьте товар в корзину!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val bundle = Bundle()
        bundle.putInt("basketId", basketId)

        findNavController().navigate(R.id.make_Order, bundle)
    }

    private fun LoadProducts(products: ArrayList<Products>) {
        recycle.adapter = BasketAdapter(products)
    }
}