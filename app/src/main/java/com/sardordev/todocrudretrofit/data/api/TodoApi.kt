package com.sardordev.todocrudretrofit.data.api

import androidx.room.Delete
import com.sardordev.todocrudretrofit.data.model.EmptyData
import com.sardordev.todocrudretrofit.data.model.ResultTodo
import com.sardordev.todocrudretrofit.data.model.TodoBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApi {



    @GET("plan/")
    suspend fun getAllTodo(): Response<List<ResultTodo>>


    @POST("plan/")
    suspend fun inserTodo(@Body todoBody: TodoBody): Response<List<ResultTodo>>


    @PUT("plan/{id}/")
    suspend fun updateItem(@Path("id") id : Int,@Body todoBody: TodoBody)


    @DELETE("plan/{id}/")
    suspend fun deleteItems(@Path("id") id : Int) : Response<EmptyData>



}