package com.example.fasol.basket

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.fasol.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class Make_Order : Fragment() {

    private lateinit var address: TextInputEditText
    private lateinit var comment: TextInputEditText
    private lateinit var rgroup : RadioGroup
    private lateinit var apply : MaterialButton
    private var basketId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_make_order, container, false)

        address = v.findViewById(R.id.address_text)
        comment = v.findViewById(R.id.comment_text)
        rgroup = v.findViewById(R.id.radioGroup)
        apply = v.findViewById(R.id.applyOrderBtn)
        basketId = requireArguments().getInt("basketId")

        apply.setOnClickListener {
            applyOrder()
        }


        return v
    }

    private fun applyOrder() {
        val user = ProfileManager.getCurrentUser()
        val selectedId = rgroup.checkedRadioButtonId
        val radioButton : RadioButton = requireView().findViewById(selectedId)

        val text = radioButton.text

        val orderType = if(text == "Самовывоз")
            "self"
        else
            "delivery"

        var createOrderModel = CreateOrderModel(user.firstName, user.lastName , user.phone, address.text.toString(), orderType, comment.text.toString())

        try {
            RetrofitClient.instance.createOrder("Bearer " + TokenManager.AccessToken, createOrderModel).enqueue(object : retrofit2.Callback<CreateOrderModel>{
                override fun onResponse(call: Call<CreateOrderModel>, response: Response<CreateOrderModel>) {
                    if(response.code() == 201)
                    {
                        try {
                            Navigation.findNavController(requireView()).navigate(R.id.main)
//                            val navController = findNavController()
//                            findNavController().navigate(R.id.ordersEmpty)
                        } catch (ex : Exception){
                            var asddasda = 0
                        }
                        Snackbar.make(view!!, "Заказ успешно создан!", Snackbar.LENGTH_LONG).show()
                    }
                    else
                        Toast.makeText(context, "Что-то пошло не так! ${response.code()} ${response.body()}", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<CreateOrderModel>, t: Throwable) {
                }
            })

        } catch (ex:Exception)
        {
            val asdsadas = 0
        }

    }
}