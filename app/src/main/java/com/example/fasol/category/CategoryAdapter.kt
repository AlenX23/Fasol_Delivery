package com.example.fasol.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.Category
import com.example.fasol.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_card.view.*

class CategoryAdapter(private val list: ArrayList<Category>, private val onClick: OnClick) :
    RecyclerView.Adapter<CategoryAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View, private val onClick: OnClick) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClick.onClick(absoluteAdapterPosition)
            }
        }

        fun bind(category: Category) {
            with(itemView) {
                Category_Title.text = category.name
                Picasso.with(context).load(category.representation).fit().into(Category_Image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.category_card, parent, false)
        return PostViewHolder(view, onClick)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnClick {
        fun onClick(position: Int)
    }
}