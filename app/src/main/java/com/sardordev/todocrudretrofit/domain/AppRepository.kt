package com.sardordev.todocrudretrofit.domain

import com.sardordev.todocrudretrofit.data.model.EmptyData
import com.sardordev.todocrudretrofit.data.model.ResultTodo
import com.sardordev.todocrudretrofit.data.model.TodoBody
import com.sardordev.todocrudretrofit.utils.ResourceEvent

interface AppRepository {

    suspend fun getAllTodo(): ResourceEvent<List<ResultTodo>>
    suspend fun insertTodo(todoBody: TodoBody) : ResourceEvent<List<ResultTodo>>
    suspend fun updateItem(id:Int,todoBody: TodoBody)
    suspend fun deleteItem(id:Int) : ResourceEvent<EmptyData>


}