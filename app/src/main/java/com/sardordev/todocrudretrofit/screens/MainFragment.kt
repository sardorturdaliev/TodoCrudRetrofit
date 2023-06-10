package com.sardordev.todocrudretrofit.screens

import android.app.AlertDialog
import android.content.ClipData.Item
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sardordev.todocrudretrofit.R
import com.sardordev.todocrudretrofit.data.model.ResultTodo
import com.sardordev.todocrudretrofit.data.model.TodoBody
import com.sardordev.todocrudretrofit.databinding.CustomDialogBinding
import com.sardordev.todocrudretrofit.databinding.FragmentMainBinding
import com.sardordev.todocrudretrofit.screens.adapter.TodoAdapter
import com.sardordev.todocrudretrofit.utils.ItemClickListener
import com.sardordev.todocrudretrofit.utils.LongClick
import com.sardordev.todocrudretrofit.utils.UiEvent
import com.sardordev.todocrudretrofit.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment : Fragment(), ItemClickListener, LongClick {
    private val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()
    private val todoAdapter = TodoAdapter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllTodo()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getAllUsersTodo()

        initRv()

        binding.btnfloat.setOnClickListener {
            customDialog()
        }

        binding.swipeContainer.setOnRefreshListener {
            refreshItems()
        }








        return binding.root
    }

    private fun getAllUsersTodo() {
        lifecycleScope.launchWhenCreated {
            viewModel.itemobserver.collectLatest {
                when (it) {
                    UiEvent.Empty -> Unit
                    is UiEvent.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                    UiEvent.Loading -> {
                        binding.progressbar.isVisible = true
                    }
                    is UiEvent.Success<*> -> {
                        binding.progressbar.isVisible = false
                        val todoList = it.data as List<ResultTodo>
                        todoAdapter.submitList(todoList)
                        Log.d("YYY", it.data.toString())
                    }
                }
            }
        }
    }

    private fun initRv() {
        binding.rvtodos.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        binding.btnfloat.hide()
                    } else {
                        binding.btnfloat.show()
                    }
                }
            })
        }
    }

    /*
     Add Item
     */

    private fun customDialog() {
        val alertdialog = AlertDialog.Builder(requireContext())
        val view = CustomDialogBinding.inflate(layoutInflater)
        alertdialog.setView(view.root)
        val dialog = alertdialog.show()
        view.btnSave.setOnClickListener {
            if (view.edsarlavha.text.isNotEmpty()
                && view.edmatn.text.isNotEmpty()
                && view.edmuddati.text.isNotEmpty()
                && view.edsarlavha.text.isNotEmpty()
            ) {
                viewModel.insertTodo(
                    TodoBody(
                        view.edsarlavha.text.toString(),
                        view.edmatn.text.toString(),
                        view.edmuddati.text.toString(),
                        view.edsarlavha.text.toString()
                    )
                )
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                viewModel.getAllTodo()
            } else {
                Toast.makeText(requireContext(), "not enough", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.create()
    }

    private fun refreshItems() {
        binding.swipeContainer.isRefreshing = false
        viewModel.getAllTodo()
    }

    /*
    When Clicked Updated Data
     */

    override fun clickItem(resultTodo: TodoBody, pos: Int) {
        val alertdialog = AlertDialog.Builder(requireContext())
        val view = CustomDialogBinding.inflate(layoutInflater)
        alertdialog.setView(view.root)
        val dialog = alertdialog.show()

        view.edmatn.setText(resultTodo.matn)
        view.edmuddati.setText(resultTodo.oxirgi_muddat)
        view.edsarlavha.setText(resultTodo.sarlavha)
        view.holat.setText(resultTodo.holat)

        view.btnSave.setOnClickListener {
            if (view.edsarlavha.text.isNotEmpty()
                && view.edmatn.text.isNotEmpty()
                && view.edmuddati.text.isNotEmpty()
                && view.edsarlavha.text.isNotEmpty()
            ) {
                viewModel.updateItem(
                    pos,
                    TodoBody(
                        view.holat.text.toString(),
                        view.edmatn.text.toString(),
                        view.edmuddati.text.toString(),
                        view.edsarlavha.text.toString()
                    )
                )

                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                viewModel.getAllTodo()
            } else {
                Toast.makeText(requireContext(), "not enough", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.create()


        deleteSwipe(pos)

    }


    private fun deleteSwipe(id: Int) {
        lifecycleScope.launchWhenCreated {
            viewModel.deleteobserver.collectLatest {
                when (it) {
                    UiEvent.Empty -> Unit
                    is UiEvent.Error -> {

                    }
                    UiEvent.Loading -> {

                    }
                    is UiEvent.Success<*> -> {

                    }
                }
            }
        }
    }


    override fun clickLong(id: Int) {
        viewModel.deleteItem(id)
    }


}