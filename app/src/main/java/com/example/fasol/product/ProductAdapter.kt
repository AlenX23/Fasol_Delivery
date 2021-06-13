package com.example.fasol.product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.OneProduct
import com.example.fasol.ProductFromAll
import com.example.fasol.R
import com.example.fasol.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_card.product_image
import kotlinx.android.synthetic.main.product_card.product_name
import kotlinx.android.synthetic.main.product_card.product_price
import kotlinx.android.synthetic.main.product_card.view.*
import kotlinx.android.synthetic.main.product_info.*
import retrofit2.Call
import retrofit2.Response

class ProductAdapter(
    private val list: ArrayList<ProductFromAll>,
    val supportFragmentManager: FragmentManager
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private var productId: Int = 0

        private lateinit var productInfo: ProductFromAll

        @SuppressLint("SetTextI18n")
        fun bind(product: ProductFromAll) {
            with(itemView) {
                productInfo = product
                productId = product.id

                Picasso.with(context).load(product.representation).fit().into(product_image)
                product_name.text = product.name
                product_price.text = product.price + " ₽"
            }
        }

        init {
            itemView.setOnClickListener {

                RetrofitClient.instance.getOneProduct(productId)
                    .enqueue(object : retrofit2.Callback<OneProduct> {

                        override fun onResponse(
                            call: Call<OneProduct>,
                            response: Response<OneProduct>
                        ) {
                            if (response.code() == 200) {
                                val info = ProductInfo()

                                info.showNow(supportFragmentManager, "ProductInfo")

                                Picasso.with(it.context).load(response.body()!!.representation)
                                    .fit().into(info.product_image)
                                info.product_name.text = response.body()!!.name
                                info.product_price.text = "Цена: " + response.body()!!.price + " ₽"
                                info.product_description.text = response.body()!!.description
                            } else {
                                Toast.makeText(
                                    it.context,
                                    "Что-то пошло не так! ${response.code()} ${response.body()}",
                                    Toast.LENGTH_SHORT
                                )
                            }
                        }

                        override fun onFailure(call: Call<OneProduct>, t: Throwable) {
                            Toast.makeText(it.context, "Что-то пошло не так!", Toast.LENGTH_SHORT)
                        }

                    })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        holder.bind(list[position])
    }
}