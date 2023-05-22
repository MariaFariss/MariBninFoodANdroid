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
import com.example.maribninfood.model.Categories
import com.example.maribninfood.model.RecipeClass


class SubCategoryAdapter(private val dataList: ArrayList<Categories>): RecyclerView.Adapter<SubCategoryAdapter.ViewHolderClass>(){

    var onItemClick: ((Categories) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_sub_category, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
        ////pour l'image/////
        Glide.with(App.applicationContext)
            .load(Uri.parse(currentItem.image))
            .into(holder.rvImage)
        holder.rvTitle.text = currentItem.type
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
//        val backArrowButton: TextView = itemView.findViewById(R.id.arrowBack)
    }

}