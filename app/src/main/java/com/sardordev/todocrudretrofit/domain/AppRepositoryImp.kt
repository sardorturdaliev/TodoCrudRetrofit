package com.sardordev.todocrudretrofit.domain

import com.sardordev.todocrudretrofit.data.api.TodoApi
import com.sardordev.todocrudretrofit.data.model.EmptyData
import com.sardordev.todocrudretrofit.data.model.ResultTodo
import com.sardordev.todocrudretrofit.data.model.TodoBody
import com.sardordev.todocrudretrofit.utils.ResourceEvent
import javax.inject.Inject

class AppRepositoryImp @Inject constructor(private val api: TodoApi) : AppRepository {


    override suspend fun getAllTodo(): ResourceEvent<List<ResultTodo>> {
        return try {
            val result = api.getAllTodo()
            if (result.isSuccessful) {
                ResourceEvent.Success(result.body())
            } else {
                ResourceEvent.Error(message = result.message())
            }
        } catch (e: Exception) {
            ResourceEvent.Error(message = e.message)
        }
    }



    override suspend fun insertTodo(todoBody: TodoBody): ResourceEvent<List<ResultTodo>> {
        return try {
            val result = api.inserTodo(todoBody)
            if (result.isSuccessful) {
                ResourceEvent.Success(result.body())
            } else {
                ResourceEvent.Error(message = result.message())
            }
        } catch (e: Exception) {
            ResourceEvent.Error(message = e.message)
        }
    }




    override suspend fun updateItem(id: Int, todoBody: TodoBody) {
        api.updateItem(id, todoBody)
    }


    override suspend fun deleteItem(id: Int): ResourceEvent<EmptyData> {
        return try {
            val result = api.deleteItems(id)
            if (result.isSuccessful) {
                ResourceEvent.Success(result.body())
            } else {
                ResourceEvent.Error(message = result.message())
            }
        } catch (e: Exception) {
            ResourceEvent.Error(message = e.message)
        }
    }

}