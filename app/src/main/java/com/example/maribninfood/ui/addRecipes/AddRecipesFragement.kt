package com.example.maribninfood.ui.addRecipes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.maribninfood.R
import com.example.maribninfood.dao.RecipeDao
import com.example.maribninfood.model.RecipeClass

class addRecipesFragement : Fragment() {

    companion object {
        private lateinit var title : TextView
        private lateinit var description : TextView
        private lateinit var instructions : TextView
        private lateinit var category: Spinner
        private lateinit var image : TextView
        private lateinit var calories : TextView
        private lateinit var time : TextView
        private lateinit var addButton: Button
        fun newInstance() = addRecipesFragement()
    }

    private lateinit var viewModel: AddRecipesFragementViewModel
    // Create a HashMap to map the category names to Firestore document IDs
    private val categoryMap = hashMapOf(
        "starter" to "Categories/D0wQQrfNF4h5BfFbKMqZ",
        "dessert" to "Categories/kJWSyOUsHa7lvv7u7WuA",
        "drinks" to "Categories/2EdCSS6s8PqEuwKznEZI",
        "main course" to "Categories/Ozgsfm4aP3jFUB0TD0T6"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_add_recipes_fragement, container, false)
        title = view.findViewById(R.id.title)
        description = view.findViewById(R.id.description)
        instructions = view.findViewById(R.id.tvInstructions)
        category = view.findViewById(R.id.categorySpinner)
        image = view.findViewById(R.id.image)
        calories = view.findViewById(R.id.calories_add)
        time = view.findViewById(R.id.prep_time)
        addButton = view.findViewById(R.id.add_recipes_button)

        //spinner category
        val categorySpinner: Spinner = view.findViewById(R.id.categorySpinner)
        val dropDownList = arrayOf("starter", "dessert", "drinks", "main course")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            dropDownList
        )
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter


        addButton.setOnClickListener {
            val title = title.text.toString()
            val description = description.text.toString()
            val ingredients = instructions.text.toString()
            val category = category.selectedItem.toString()
            val image = image.text.toString()
            val calories = calories.text.toString()
            val time = time.text.toString()
            val new = true

            // Validate the fields
            if (title.isBlank() || description.isBlank() || ingredients.isBlank() || image.isBlank() ||
                calories.isBlank() || time.isBlank()) {
                // Show an error message or handle the invalid fields
                Toast.makeText(requireContext(), "Please fill in all the required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get the corresponding Firestore document ID from the categoryMap
            val categoryDocumentId = categoryMap[category] ?: ""

            val newRecipe = RecipeClass(
                "1",
                image,
                title,
                description,
                new,
                categoryDocumentId,
                ingredients,
                calories,
                time
            )
            RecipeDao.addRecipe(newRecipe) {
                findNavController().navigate(R.id.navigation_account)
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddRecipesFragementViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        title.text = null
        description.text = null
        instructions.text = null
        image.text = null
        calories.text = null
        time.text = null
        category.setSelection(0)
    }

}