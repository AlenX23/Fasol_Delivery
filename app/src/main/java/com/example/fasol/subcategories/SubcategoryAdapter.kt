package com.example.fasol.subcategories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.R
import com.example.fasol.Subcategory
import kotlinx.android.synthetic.main.category_card.view.*
import kotlinx.android.synthetic.main.category_card.view.Category_Title
import kotlinx.android.synthetic.main.fragment_subcategories.view.*
import kotlinx.android.synthetic.main.subcategory_card.view.*

class SubcategoryAdapter(private val list: ArrayList<Subcategory>) :
    RecyclerView.Adapter<SubcategoryAdapter.SubcategoryViewHolder>() {

    inner class SubcategoryViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(subcategory: Subcategory) {
            with(itemView) {
                Category_Title.text = subcategory.category
                Subcategory_Title.text = subcategory.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryViewHolder =
        SubcategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.subcategory_card, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SubcategoryAdapter.SubcategoryViewHolder, position: Int) {
        holder.bind(list[position])
    }
}