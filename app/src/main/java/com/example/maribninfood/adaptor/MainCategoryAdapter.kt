package com.example.maribninfood.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.maribninfood.R
import com.example.maribninfood.model.RecipeClass


class MainCategoryAdapter(private val dataList: ArrayList<RecipeClass>): RecyclerView.Adapter<MainCategoryAdapter.ViewHolderClass>(){

    var onItemClick: ((RecipeClass) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_main_category, parent, false)
        return ViewHolderClass(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
//        holder.rvImage.setImageResource(currentItem.dataImage)
        holder.rvTitle.text = currentItem.dataTitle
        holder.rvTitle.text = currentItem.dataImage
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvImage: ImageView = itemView.findViewById(R.id.img_dish)
        val rvTitle: TextView = itemView.findViewById(R.id.tv_dish_name)
    }

}