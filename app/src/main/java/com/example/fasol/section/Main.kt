package com.example.fasol.section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.graphics.isWideGamut
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fasol.Category
import com.example.fasol.R
import com.example.fasol.RetrofitClient
import com.example.fasol.category.CategoryAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Main : Fragment(R.layout.fragment_main), CategoryAdapter.OnItemClickListener{
    val list = ArrayList<Category>()
    val adapter = CategoryAdapter(list, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryView()
    }

    private fun categoryView() {
        Category_View.setHasFixedSize(true)
        Category_View.layoutManager = GridLayoutManager(requireContext().applicationContext, 2)

        RetrofitClient.instance.getCategory().enqueue(object : Callback<List<Category>> {

            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                response.body()!!.let { list.addAll(it) }

                Category_View.adapter = adapter
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
            }
        })
    }

    override fun onItemClick(position: Int) {
        /*val action = MainDirections.actionMainToSubcategoriesFragment(list[position].id)
        findNavController().navigate(action)

        Toast.makeText(context, list[id].id, Toast.LENGTH_LONG).show()*/

        //makeText(this, list[id].id, Toast.LENGTH_LONG).show()
    }
}