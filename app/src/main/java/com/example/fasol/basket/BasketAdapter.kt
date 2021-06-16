package com.example.fasol.product

import TokenManager
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.ChangeBasketCountModel
import com.example.fasol.Products
import com.example.fasol.R
import com.example.fasol.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.basket_card.view.*
import retrofit2.Call
import retrofit2.Response

class BasketAdapter(
    private val list: ArrayList<com.example.fasol.Products>
) :
    RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    inner class BasketViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private var productId = 0
        private lateinit var productPrice: TextView
        private lateinit var productCount: TextView

        @SuppressLint("SetTextI18n")
        fun bind(product: Products) {
            with(itemView) {
                productId = product.id
                productCount = textView6
                productPrice = textView5
                item_name.text = product.product.name
                textView5.text = product.totalPrice
                textView6.text = product.quantity.toString()

                Picasso.with(context)
                    .load("https://fasoldelivery-admin.ru/" + product.product.representation)
                    .fit().into(imageView)

                substractBtn.setOnClickListener {
                    substract()
                }

                additionBtn.setOnClickListener {
                    addition()
                }

                imageButton.setOnClickListener {
                    removeFromBasket()
                }
            }
        }

        private fun removeFromBasket() {
            RetrofitClient.instance.deleteFromBasket(
                "Bearer " + TokenManager.AccessToken,
                productId.toLong()
            )
                .enqueue(object : retrofit2.Callback<Void> {

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (response.code() == 204) {
                            list.removeAt(position)
                            notifyDataSetChanged()
                        } else
                            Toast.makeText(
                                itemView.context,
                                "Что-то пошло не так! ${response.code()} ${response.body()}",
                                Toast.LENGTH_SHORT
                            )
                    }

                    override fun onFailure(
                        call: Call<Void>,
                        t: Throwable
                    ) {
                        list.removeAt(position)
                        notifyDataSetChanged()
                    }
                })
        }

        private fun substract() {
            if (productCount.text.toString() == "1") {
                RetrofitClient.instance.deleteFromBasket(
                    "Bearer " + TokenManager.AccessToken,
                    productId.toLong()
                )
                    .enqueue(object : retrofit2.Callback<Void> {

                        override fun onResponse(
                            call: Call<Void>,
                            response: Response<Void>
                        ) {
                            if (response.code() == 204) {
                                list.removeAt(position)
                                notifyDataSetChanged()
                            } else
                                Toast.makeText(
                                    itemView.context,
                                    "Что-то пошло не так! ${response.code()} ${response.body()}",
                                    Toast.LENGTH_SHORT
                                )
                        }

                        override fun onFailure(
                            call: Call<Void>,
                            t: Throwable
                        ) {
                            list.removeAt(position)
                            notifyDataSetChanged()
                        }
                    })

                return
            }

            RetrofitClient.instance.changeCountBasket(
                "Bearer " + TokenManager.AccessToken,
                ChangeBasketCountModel(productId, "subtraction")
            )
                .enqueue(object : retrofit2.Callback<ChangeBasketCountModel> {
                    override fun onResponse(
                        call: Call<ChangeBasketCountModel>,
                        response: Response<ChangeBasketCountModel>
                    ) {
                        if (response.code() == 200) {
                            var priceAs4islo = (productPrice.text.toString()
                                .toDouble() / productCount.text.toString().toInt())
                            var newCount = productCount.text.toString().toInt() - 1
                            productPrice.text = ((productPrice.text.toString()
                                .toDouble() / productCount.text.toString()
                                .toInt()) * (productCount.text.toString().toInt() - 1)).toString()
                            productCount.text =
                                (productCount.text.toString().toInt() - 1).toString()
                        } else
                            Toast.makeText(
                                itemView.context,
                                "Что-то пошло не так! ${response.code()} ${response.body()}",
                                Toast.LENGTH_SHORT
                            )
                    }

                    override fun onFailure(
                        call: Call<ChangeBasketCountModel>,
                        t: Throwable
                    ) {
                        Toast.makeText(itemView.context, "Что-то пошло не так!", Toast.LENGTH_SHORT)
                    }
                })
        }

        private fun addition() {
            RetrofitClient.instance.changeCountBasket(
                "Bearer " + TokenManager.AccessToken,
                ChangeBasketCountModel(productId, "addition")
            )
                .enqueue(object : retrofit2.Callback<ChangeBasketCountModel> {
                    override fun onResponse(
                        call: Call<ChangeBasketCountModel>,
                        response: Response<ChangeBasketCountModel>
                    ) {
                        if (response.code() == 200) {
                            var priceAs4islo = (productPrice.text.toString()
                                .toDouble() / productCount.text.toString().toInt())
                            val newCount = productCount.text.toString().toInt() + 1
                            priceAs4islo *= newCount
                            productPrice.text = priceAs4islo.toString()
                            productCount.text =
                                (productCount.text.toString().toInt() + 1).toString()
                        } else
                            Toast.makeText(
                                itemView.context,
                                "Что-то пошло не так! ${response.code()} ${response.body()}",
                                Toast.LENGTH_SHORT
                            )
                    }

                    override fun onFailure(
                        call: Call<ChangeBasketCountModel>,
                        t: Throwable
                    ) {
                        Toast.makeText(itemView.context, "Что-то пошло не так!", Toast.LENGTH_SHORT)
                    }
                })
        }
    }

    fun clearItems() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder =
        BasketViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.basket_card, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BasketAdapter.BasketViewHolder, position: Int) {
        holder.bind(list[position])
    }
}