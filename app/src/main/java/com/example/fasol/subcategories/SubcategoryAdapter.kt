package com.example.fasol.subcategories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.R
import com.example.fasol.SubcategoryModel
import kotlinx.android.synthetic.main.subcategory_card.view.*

class SubcategoryAdapter(private val list: ArrayList<SubcategoryModel>) :
    RecyclerView.Adapter<SubcategoryAdapter.SubcategoryViewHolder>() {

    inner class SubcategoryViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private var name = ""
        private var subcatId = 0

        fun bind(subcategory: SubcategoryModel) {
            with(itemView) {
                name = subcategory.name
                subcatId = subcategory.id
                Subcategory_Title.text = subcategory.name
            }
        }

        init {
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("subcatId", subcatId)
                bundle.putString("name", name)
                itemView.findNavController().navigate(R.id.products, bundle)
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