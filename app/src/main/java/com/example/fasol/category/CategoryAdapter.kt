package com.example.fasol.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_card.view.*
import retrofit2.Call
import retrofit2.Response

class CategoryAdapter(
    private val list: ArrayList<Category>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CategoryAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var id: Int = 0

        fun bind(category: Category) {
            with(itemView) {
                id = category.id

                Category_Title.text = category.name
                Picasso.with(context).load(category.representation).fit().into(Category_Image)
            }
        }

        init {
            itemView.setOnClickListener(){
                RetrofitClient.instance.getSubcategories(id).enqueue(object : retrofit2.Callback<List<SubcategoryModel>> {

                    /*fun onResponse(call: Call<ArrayList<Subcategory>>, response: Response<ArrayList<Subcategory>>) {
                        when {
                            response.code() == 200 -> {
                                TokenManager.AccessToken = response.body()?.access!!
                                TokenManager.RefreshToken = response.body()?.refresh!!
                                GetUser()
                            }
                            response.code() == 401 -> {
                                Toast.makeText(context, "Неверный логин или пароль!", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(
                                    context,
                                    "Что-то пошло не так! ${response.code()} ${response.message()}\"",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }*/

                    override fun onResponse(
                        call: Call<List<SubcategoryModel>>, response: Response<List<SubcategoryModel>>) {


                    }

                    override fun onFailure(call: Call<List<SubcategoryModel>>, t: Throwable) {
                    }

                } )
            }
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            listener.onItemClick(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.category_card, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}