package com.sardordev.todocrudretrofit.utils

import com.sardordev.todocrudretrofit.data.model.ResultTodo
import com.sardordev.todocrudretrofit.data.model.TodoBody

interface ItemClickListener {

    fun clickItem(resultTodo: TodoBody,pos:Int)

}