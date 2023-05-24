package com.example.maribninfood.adaptor

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maribninfood.App
import com.example.maribninfood.DetailActivity
import com.example.maribninfood.R
import com.example.maribninfood.dao.SaveDao
import com.example.maribninfood.model.Categories
import com.example.maribninfood.model.RecipeClass
import com.example.maribninfood.model.SaveRecipe


class ShowCategoryRecipeAdapter(private val dataList: ArrayList<RecipeClass>,private val layoutResId: Int): RecyclerView.Adapter<ShowCategoryRecipeAdapter.ViewHolderClass>() {
    var onItemClick: ((RecipeClass) -> Unit)? = null
    var onDeleteClick: ((RecipeClass) -> Unit)? = null
    var onBackArrowClick: ((RecipeClass) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return ViewHolderClass(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
        Log.d("main" ,""+ currentItem.dataImage)
        Glide.with(App.applicationContext)
            .load(Uri.parse(currentItem.dataImage))
            .into(holder.rvImage)
//        holder.rvImage.setImageResource(currentItem.dataImage)
        holder.rvTitle.text = currentItem.dataTitle
        holder.rvBoutton.setOnClickListener{
            onItemClick?.invoke(currentItem)
        }
        holder.deleteButton?.setOnClickListener{
            onDeleteClick?.invoke(currentItem)
        }
        //pour les calories
        holder.caloriesButton.text = currentItem.calories + " cal"
        //pour le temps de preparation
        holder.prepButton.text = currentItem.prep + " min"
//        //back arrow button
//        holder.backArrowButton.setOnClickListener{
//            onBackArrowClick?.invoke(currentItem)
//
//        }

    }
    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvImage: ImageView = itemView.findViewById(R.id.img_dish)
        val rvTitle: TextView = itemView.findViewById(R.id.tv_dish_name)
        val rvBoutton: TextView = itemView.findViewById(R.id.btn_more_info)
        val deleteButton: TextView? = itemView.findViewById(R.id.btn_delete)
        val caloriesButton: TextView = itemView.findViewById(R.id.cal)
        val prepButton: TextView = itemView.findViewById(R.id.tvTime)
//        val backArrowButton: ImageView = itemView.findViewById(R.id.arrowBack)
    }
}