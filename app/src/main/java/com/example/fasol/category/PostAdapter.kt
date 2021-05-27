package com.example.fasol.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_card.view.*

class PostAdapter(
    private val list: List<Category>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        fun bind(category: Category) {
            with(itemView) {
                Category_Title.text = category.name
                Picasso.with(context).load(category.representation).fit().into(Category_Image)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_card, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])

    }
}