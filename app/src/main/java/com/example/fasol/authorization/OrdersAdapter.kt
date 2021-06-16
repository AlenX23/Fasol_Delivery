package com.example.fasol.authorization

import TokenManager
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.*
import kotlinx.android.synthetic.main.ready_order_card.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersAdapter(
    private val list: ArrayList<OrderModel>
) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private lateinit var basket: Basket

        @SuppressLint("SetTextI18n")
        fun bind(order: OrderModel) {
            with(itemView) {
                order_number.text = order.id.toString()
                order_date.text = order.order_date
                order_address.text = order.address
                order_amount.text = order.basket.totalPrice
                basket = order.basket
                status.text = order.status

                val pdcts = ArrayList<OneBasketInOrder>()

                order.basket.products.forEach {
                    pdcts.add(
                        OneBasketInOrder(
                            it.product.name,
                            it.quantity.toString() + "шт, " + it.totalPrice + "р."
                        )
                    )
                }

                remove_order_btn.setOnClickListener {
                    changeStatusOrder(order.id)
                }

                itemView.setOnClickListener {
                    val adapter =
                        OrdersListAdapter(pdcts)

                    val dialog =
                        LayoutInflater.from(it.context).inflate(R.layout.order_basket_card, null)
                    val builder = AlertDialog.Builder(it.context).setView(dialog)

                    val recycle = dialog.findViewById<RecyclerView>(R.id.listView)
                    recycle.setHasFixedSize(true)
                    recycle.layoutManager =
                        GridLayoutManager(context.applicationContext, 1)
                    recycle.adapter = adapter

                    val alDialog = builder.create()
                    alDialog.show()
                }
            }
        }

        private fun changeStatusOrder(id: Int) {

            RetrofitClient.instance.changeStatusOrder(
                "Bearer " + TokenManager.AccessToken,
                ChangeOrderModel("cancelled"),
                id.toLong()
            ).enqueue(object : Callback<ChangeOrderModel> {
                override fun onResponse(
                    call: Call<ChangeOrderModel>,
                    response: Response<ChangeOrderModel>
                ) {
                    if (response.code() == 202) {
                        removeOrder(id)
                    }
                }

                override fun onFailure(call: Call<ChangeOrderModel>, t: Throwable) {
                    val ue = 0
                }

            })
        }

        private fun removeOrder(id: Int) {
            RetrofitClient.instance.removeOrder("Bearer " + TokenManager.AccessToken, id.toLong())
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.code() == 204) {
                            list.removeAt(position)
                            notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                    }

                })
        }
    }

    fun clearItems() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder =
        OrderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.ready_order_card, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: OrdersAdapter.OrderViewHolder, position: Int) {
        holder.bind(list[position])
    }
}