package com.example.fasol

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fasol.category.Category
import com.example.fasol.category.PostAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), Communicator, PostAdapter.OnItemClickListener {
    private val list = ArrayList<Category>()
    private val adapter = PostAdapter(list, this)

    private lateinit var communicator: Communicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.mainFragment)

        bottomNavigationView.setupWithNavController(navController)

        Category_View.setHasFixedSize(true)
        Category_View.layoutManager = GridLayoutManager(this, 2)

        RetrofitClient.instance.getCategory().enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                response.body()?.let { list.addAll(it) }

                Category_View.adapter = adapter
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
            }
        })
    }

    override fun passData(container: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(container, fragment)
            .commit()
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
    }
}