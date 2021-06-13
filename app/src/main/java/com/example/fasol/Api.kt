package com.example.fasol

import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("api/categories/")
    fun getCategory(): Call<List<Category>>

    //@kotlinx.serialization.ImplicitReflectionSerializer
    @POST("api/token/")
    @Headers("Content-Type: application/json")
    fun getToken(@Body body: PhonePasswordModel): Call<TokenResponce>

    //@kotlinx.serialization.ImplicitReflectionSerializer
    @POST("api/account/register/")
    @Headers("Content-Type: application/json")
    fun registerUser(@Body body: PhonePasswordModel): Call<RegisterUserResponce>

    @PATCH("api/account/user/")
    @Headers("Content-Type: application/json")
    fun changeUsername(
        @Body body: UserWithoutId,
        @Header("Authorization") Authorization: String
    ): Call<User>

    @PATCH("api/account/user/")
    @Headers("Content-Type: application/json")
    fun getUser(@Header("Authorization") Authorization: String): Call<User>

    //@kotlinx.serialization.ImplicitReflectionSerializer
    @POST("api/token/refresh/")
    @Headers("Content-Type: application/json")
    fun updateAccessToken(@Body body: RefreshModel): Call<TokenResponce>

    @GET("api/basket/")
    fun getBasket(@Header("Authorization") Authorization: String): Call<Basket>

    @POST("api/basket/add-to-basket/")
    @Headers("Content-Type: application/json")
    fun addToBasket(
        @Header("Authorization") Authorization: String,
        @Body body: AddToBasketModel
    ): Call<AddToBasketModel>

    @POST("api/basket/change-product-qty/")
    @Headers("Content-Type: application/json")
    fun changeCountBasket(
        @Header("Authorization") Authorization: String,
        @Body body: ChangeBasketCountModel
    ): Call<ChangeBasketCountModel>

    @DELETE("api/basket/remove-from-basket/{id}")
    fun deleteFromBasket(
        @Header("Authorization") Authorization: String,
        @Path("id") id: Long
    ): Call<Void>

    @GET("api/subcategories/")
    fun getSubcategories(@Query("category") category: Int): Call<List<SubcategoryModel>>

    @GET("api/products/")
    fun getProducts(@Query("search") subCategoryName: String): Call<ProductsModel>

    @GET("api/products/{id}")
    fun getOneProduct(@Path("id") productId: Int): Call<OneProduct>

    @GET("api/orders/")
    fun getOrders(@Header("Authorization") Authorization: String): Call<List<OrderModel>>

    @GET("api/orders/order-create/")
    fun createOrder(@Header("Authorization") Authorization: String, @Body createOrder:CreateOrderModel): Call<CreateOrderModel>
}