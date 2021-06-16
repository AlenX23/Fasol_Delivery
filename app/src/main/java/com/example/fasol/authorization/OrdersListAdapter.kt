package com.example.fasol.authorization

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.OneBasketInOrder
import com.example.fasol.R
import kotlinx.android.synthetic.main.one_order_stroke.view.*
import java.util.*

class OrdersListAdapter(
    private val list: ArrayList<OneBasketInOrder>
) :
    RecyclerView.Adapter<OrdersListAdapter.OrdersListViewHolder>() {

    inner class OrdersListViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(product: OneBasketInOrder) {
            with(itemView) {
                product_name.text = product.name
                product_info.text = product.moreInfo
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersListViewHolder =
        OrdersListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.one_order_stroke, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: OrdersListAdapter.OrdersListViewHolder, position: Int) {
        holder.bind(list[position])
    }
}