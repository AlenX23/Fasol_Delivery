package com.example.fasol.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.OrderModel
import com.example.fasol.R
import com.example.fasol.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class orders_list : Fragment() {
    private lateinit var recycle : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_orders_list, container, false)

        recycle = v.findViewById(R.id.OrdersView)
        recycle.setHasFixedSize(true)
        recycle.layoutManager =
            GridLayoutManager(requireContext(), 1)

        RetrofitClient.instance.getOrders("Bearer " + TokenManager.AccessToken).enqueue(object :
            retrofit2.Callback<List<OrderModel>> {

            override fun onResponse(call: Call<List<OrderModel>>, response: Response<List<OrderModel>>) {
                if (response.code() == 200) {
                    if(response.body()!!.isNotEmpty())
                    {
                        recycle.adapter = OrdersAdapter(response.body()!! as ArrayList<OrderModel>)
                    }
                }
            }

            override fun onFailure(call: Call<List<OrderModel>>, t: Throwable) {
                Toast.makeText(context, "Что-то пошло не так!", Toast.LENGTH_SHORT).show()
            }
        })

        return v
    }
}