package com.example.maribninfood.ui.addRecipes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maribninfood.R

class addRecipesFragement : Fragment() {

    companion object {
        fun newInstance() = addRecipesFragement()
    }

    private lateinit var viewModel: AddRecipesFragementViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_recipes_fragement, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddRecipesFragementViewModel::class.java)
        // TODO: Use the ViewModel
    }

}