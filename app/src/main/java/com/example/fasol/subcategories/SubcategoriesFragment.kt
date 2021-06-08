package com.example.fasol.subcategories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fasol.R
import com.example.fasol.RetrofitClient
import com.example.fasol.Subcategory
import kotlinx.android.synthetic.main.fragment_subcategories.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubcategoriesFragment : Fragment(R.layout.fragment_subcategories) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_subcategories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subcategoryView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun subcategoryView() {
        val list = ArrayList<Subcategory>()
        val adapter = SubcategoryAdapter(list)

        Subcategories_View.setHasFixedSize(true)
        Subcategories_View.layoutManager = GridLayoutManager(requireContext().applicationContext, 2)

        RetrofitClient.instance.getSubcategories().enqueue(object : Callback<List<Subcategory>> {

            override fun onResponse(
                call: Call<List<Subcategory>>,
                response: Response<List<Subcategory>>
            ) {
                response.body()!!.let { list.addAll(it) }

                Subcategories_View.adapter = adapter
            }

            override fun onFailure(call: Call<List<Subcategory>>, t: Throwable) {
            }
        })
    }
}