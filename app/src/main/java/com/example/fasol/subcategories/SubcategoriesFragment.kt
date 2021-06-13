package com.example.fasol.subcategories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fasol.R
import com.example.fasol.RetrofitClient
import com.example.fasol.SubcategoryModel
import kotlinx.android.synthetic.main.fragment_subcategories.*
import retrofit2.Call
import retrofit2.Response

class SubcategoriesFragment : Fragment(R.layout.fragment_subcategories) {
    private lateinit var recycleSub: RecyclerView
    private lateinit var title: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_subcategories, container, false)

        recycleSub = v.findViewById(R.id.Subcategories_View)
        title = v.findViewById(R.id.Category_title)

        if (arguments != null) {
            val bundle = requireArguments()
            title.text = bundle.getString("name")
            RetrofitClient.instance.getSubcategories(bundle.getInt("categoryId"))
                .enqueue(object : retrofit2.Callback<List<SubcategoryModel>> {
                    override fun onResponse(
                        call: Call<List<SubcategoryModel>>,
                        response: Response<List<SubcategoryModel>>
                    ) {
                        if (response.code() == 200) {
                            val adapter =
                                SubcategoryAdapter(response.body() as ArrayList<SubcategoryModel>)
                            recycleSub.adapter = adapter

                            Subcategories_View.setHasFixedSize(true)
                            Subcategories_View.layoutManager =
                                GridLayoutManager(requireContext().applicationContext, 1)
                        } else {
                            Toast.makeText(
                                context,
                                "Что-то пошло не так! ${response.code()} ${response.body()}",
                                Toast.LENGTH_SHORT
                            )
                        }
                    }

                    override fun onFailure(call: Call<List<SubcategoryModel>>, t: Throwable) {
                        Toast.makeText(context, "Что-то пошло не так!", Toast.LENGTH_SHORT)
                    }

                })
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subcategoryView()
        super.onViewCreated(view, savedInstanceState)

        button_subcategory_back.setOnClickListener {
            findNavController().navigate(R.id.main)
        }
    }

    private fun subcategoryView() {
        val list = ArrayList<SubcategoryModel>()
        val adapter = SubcategoryAdapter(list)

        Subcategories_View.setHasFixedSize(true)
        Subcategories_View.layoutManager = GridLayoutManager(requireContext().applicationContext, 2)
    }
}