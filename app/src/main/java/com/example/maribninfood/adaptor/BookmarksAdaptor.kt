package com.example.maribninfood.adaptor

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maribninfood.App
import com.example.maribninfood.R
import com.example.maribninfood.dao.RecipeDao
import com.example.maribninfood.model.RecipeClass
import com.example.maribninfood.model.SaveRecipe
import com.google.firebase.firestore.FirebaseFirestore

class BookmarksAdaptor(private val recipes: List<SaveRecipe>) : RecyclerView.Adapter<BookmarksAdaptor.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.dishTitle)
        val image: ImageView = itemView.findViewById(R.id.img_dish)
        // Ajoutez d'autres vues pour afficher les détails de la recette
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_bookmarks, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        val recipeDetails = RecipeDao.getRecipeByRef(recipe.referenceRecipe){
            holder.title.text = it.dataTitle
            Glide.with(holder.image.context).load(it.dataImage).into(holder.image)
        }

    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    }

