package com.example.fasol

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

//Main models

data class Category(
    val id: Int,
    val name: String,
    val representation: String
)

data class PhonePasswordModel(
    val phone: String,
    val password: String
)

data class RegisterUserResponce(
    @SerializedName("user") var user: User,
    @SerializedName("message") var message: String
)

data class RefreshModel(
    @SerializedName("refresh") var refresh: String
)

//@Serializable
data class TokenResponce(
    @SerializedName("refresh") var refresh: String,
    @SerializedName("access") var access: String
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}

/*
    Submodels
 */

data class UserWithoutId(
    @SerializedName("phone") var phone: String,
    @SerializedName("first_name") var firstName: String,
    @SerializedName("last_name") var lastName: String,
    @SerializedName("address") var address: String
)

//@Serializable
data class User(

    @SerializedName("id") var id: Int,
    @SerializedName("phone") var phone: String,
    @SerializedName("first_name") var firstName: String,
    @SerializedName("last_name") var lastName: String,
    @SerializedName("address") var address: String
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}

/*
    Корзина
 */

data class Basket(
    @SerializedName("id") var id : Int,
    @SerializedName("products") var products : List<Products>,
    @SerializedName("total_products") var totalProducts : Int,
    @SerializedName("total_price") var totalPrice : String,
    @SerializedName("in_order") var inOrder : Boolean,
    @SerializedName("owner") var owner : Int
)

data class Product (
    @SerializedName("id") var id : Int,
    @SerializedName("subcategory") var subcategory : String,
    @SerializedName("name") var name : String,
    @SerializedName("representation") var representation : String,
    @SerializedName("weight") var weight : String,
    @SerializedName("composition") var composition : String,
    @SerializedName("price") var price : String,
    @SerializedName("in_stock") var inStock : Boolean
)

data class Products (
    @SerializedName("id") var id : Int,
    @SerializedName("product") var product : Product,
    @SerializedName("quantity") var quantity : Int,
    @SerializedName("total_price") var totalPrice : String
)

data class AddToBasketModel(
    @SerializedName("id") var id: Int
)

// action - операция производимаяя с количеством (addition - увуличение, subtraction - уменьшение)
data class ChangeBasketCountModel(
    @SerializedName("id") var id: Int,
    @SerializedName("action") var action: String
)

/*
    Подкатегории
 */

data class SubcategoryModel(
    @SerializedName("id") var id: Int,
    @SerializedName("category") var category: Int,
    @SerializedName("name") var name: String
)

/*
    Продукты
 */

data class ProductsModel(
    @SerializedName("links") var links: Links,
    @SerializedName("total") var total: Int,
    @SerializedName("results") var results: List<ProductFromAll>
)

data class Links(

    @SerializedName("next") var next: String,
    @SerializedName("previous") var previous: String

)

data class ProductFromAll(
    @SerializedName("id") var id: Int,
    @SerializedName("subcategory") var subcategory: String,
    @SerializedName("name") var name: String,
    @SerializedName("representation") var representation: String,
    @SerializedName("weight") var weight: String,
    @SerializedName("composition") var composition: String,
    @SerializedName("price") var price: String,
    @SerializedName("in_stock") var inStock: Boolean
)

data class OneProduct (

    @SerializedName("id") var id : Int,
    @SerializedName("subcategory") var subcategory : SubcategoryModel,
    @SerializedName("name") var name : String,
    @SerializedName("description") var description : String,
    @SerializedName("representation") var representation : String,
    @SerializedName("weight") var weight : String,
    @SerializedName("composition") var composition : String,
    @SerializedName("price") var price : String,
    @SerializedName("in_stock") var inStock : Boolean

)

/*
    Заказы
 */

data class OrderModel(
    @SerializedName("id") var id: Int,
    @SerializedName("status") var status: String,
    @SerializedName("basket") var basket: Basket,
    @SerializedName("first_name") var first_name: String,
    @SerializedName("last_name") var last_name: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("address") var address: String,
    @SerializedName("comment") var comment: String,
    @SerializedName("order_date") var order_date: String,
    @SerializedName("customer") var customer: Int
)

data class CreateOrderModel (
    @SerializedName("first_name") var firstName : String,
    @SerializedName("last_name") var lastName : String,
    @SerializedName("phone") var phone : String,
    @SerializedName("address") var address : String,
    @SerializedName("buying_type") var buyingType : String,
    @SerializedName("comment") var comment : String
)