package com.sardordev.todocrudretrofit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sardordev.todocrudretrofit.data.model.ResultTodo
import com.sardordev.todocrudretrofit.data.model.TodoBody
import com.sardordev.todocrudretrofit.domain.AppRepository
import com.sardordev.todocrudretrofit.utils.ResourceEvent
import com.sardordev.todocrudretrofit.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {
    private val _itemsobserver = MutableStateFlow<UiEvent>(UiEvent.Empty)
    val itemobserver : StateFlow<UiEvent> get() = _itemsobserver

    private val _addobserver = MutableStateFlow<UiEvent>(UiEvent.Empty)
    val addobserver : StateFlow<UiEvent> get() = _addobserver

    private val _deleteobserver = MutableStateFlow<UiEvent>(UiEvent.Empty)
    val deleteobserver : StateFlow<UiEvent> get() = _deleteobserver




    fun getAllTodo() {
        viewModelScope.launch {
            _itemsobserver.value = UiEvent.Loading
            val result = appRepository.getAllTodo()
            when (result) {
                is ResourceEvent.Error -> {
                    _itemsobserver.value = UiEvent.Error(result.message!!)
                }
                is ResourceEvent.Success -> {
                    _itemsobserver.value = UiEvent.Success(result.data)
                }
            }
        }
    }




    fun insertTodo(todoBody: TodoBody){
        viewModelScope.launch {
            _addobserver.value = UiEvent.Loading
            val result = appRepository.insertTodo(todoBody)
            when (result) {
                is ResourceEvent.Error -> {
                    _addobserver.value = UiEvent.Error(result.message!!)
                }
                is ResourceEvent.Success -> {
                    _addobserver.value = UiEvent.Success(result.data)
                }
            }
        }
    }


    fun updateItem(id:Int,todoBody: TodoBody){
        viewModelScope.launch {
            appRepository.updateItem(id,todoBody)
        }
    }

    fun deleteItem(id:Int){
        viewModelScope.launch {
            _deleteobserver.value = UiEvent.Loading
            val result = appRepository.deleteItem(id)
            when (result) {
                is ResourceEvent.Error -> {
                    _deleteobserver.value = UiEvent.Error(result.message!!)
                }
                is ResourceEvent.Success -> {
                    _deleteobserver.value = UiEvent.Success(result.data)
                }
            }
        }
    }



}