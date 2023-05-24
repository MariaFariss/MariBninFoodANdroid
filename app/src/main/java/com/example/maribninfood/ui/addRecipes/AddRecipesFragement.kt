package com.example.maribninfood.ui.addRecipes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.maribninfood.R
import com.example.maribninfood.dao.RecipeDao
import com.example.maribninfood.model.RecipeClass

class addRecipesFragement : Fragment() {

    companion object {
        private lateinit var title : TextView
        private lateinit var description : TextView
        private lateinit var ingredients : TextView
        private lateinit var category: TextView
        private lateinit var image : TextView
        private lateinit var calories : TextView
        private lateinit var time : TextView
        private lateinit var addButton: Button
        fun newInstance() = addRecipesFragement()
    }

    private lateinit var viewModel: AddRecipesFragementViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_add_recipes_fragement, container, false)
        title = view.findViewById(R.id.title)
        description = view.findViewById(R.id.description)
        ingredients = view.findViewById(R.id.category)
        category = view.findViewById(R.id.category)
        image = view.findViewById(R.id.image)
        calories = view.findViewById(R.id.calories_add)
        time = view.findViewById(R.id.prep_time)
        addButton = view.findViewById(R.id.add_recipes_button)

        addButton.setOnClickListener {
            val title = title.text.toString()
            val description = description.text.toString()
            val ingredients = ingredients.text.toString()
            val category = category.text.toString()
            val image = image.text.toString()
            val calories = calories.text.toString()
            val time = time.text.toString()
            val new = true
//            class RecipeClass (var id: String,var dataImage:String, var dataTitle:String, var dataDesc: String, var newRecipes : Boolean, var category : String, var instruction : String, var calories:String, var prep : String):
            val newRecipe = RecipeClass("1",image,title,description,new,category,ingredients,calories,time)
            RecipeDao.addRecipe(newRecipe){
                findNavController().navigate(R.id.navigation_editPofile)

            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddRecipesFragementViewModel::class.java)
        // TODO: Use the ViewModel
    }

}