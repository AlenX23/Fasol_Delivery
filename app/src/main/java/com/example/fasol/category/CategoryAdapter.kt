package com.example.fasol.category

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.Category
import com.example.fasol.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_card.view.*

class CategoryAdapter(
    private val list: ArrayList<Category>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CategoryAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var categoryId: Int = 0
        private lateinit var categoryName: String
        private val context = itemView.context

        fun bind(category: Category) {
            with(itemView) {
                categoryId = category.id
                categoryName = category.name
                Category_Title.text = category.name
                val url = Uri.decode(category.representation)
                Picasso.with(context).load(url).fit().into(Category_Image)
            }
        }

        init {
            itemView.setOnClickListener() {
                val bundle = Bundle()
                bundle.putInt("categoryId", categoryId)
                bundle.putString("name", categoryName)
                itemView.findNavController().navigate(R.id.subcategoriesFragment, bundle)
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