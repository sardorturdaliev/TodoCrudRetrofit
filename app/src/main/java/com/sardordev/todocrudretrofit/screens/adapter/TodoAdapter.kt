package com.sardordev.todocrudretrofit.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sardordev.todocrudretrofit.data.model.ResultTodo
import com.sardordev.todocrudretrofit.data.model.TodoBody
import com.sardordev.todocrudretrofit.databinding.ItemCardBinding
import com.sardordev.todocrudretrofit.utils.ItemClickListener
import com.sardordev.todocrudretrofit.utils.LongClick

class TodoAdapter(private val listener: ItemClickListener, private val listenerlong: LongClick) :
    ListAdapter<ResultTodo, TodoAdapter.VH>(diff) {

    inner class VH(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onbind(todo: ResultTodo) {
            binding.tvsarlavha.text = todo.sarlavha
            binding.tvmatn.text = todo.matn
            binding.tvholat.text = todo.holat
            binding.tvmuddat.text = todo.oxirgi_muddat


            itemView.setOnClickListener {
                listener.clickItem(
                    TodoBody(
                        todo.holat,
                        todo.matn,
                        todo.oxirgi_muddat,
                        todo.sarlavha
                    ), todo.id
                )
            }

            itemView.setOnLongClickListener {
                listenerlong.clickLong(todo.id)
                true
            }


        }

    }


    object diff : DiffUtil.ItemCallback<ResultTodo>() {
        override fun areItemsTheSame(oldItem: ResultTodo, newItem: ResultTodo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultTodo, newItem: ResultTodo): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onbind(getItem(position))
    }

}