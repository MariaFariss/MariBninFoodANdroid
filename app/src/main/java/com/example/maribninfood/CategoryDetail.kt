package com.example.maribninfood

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maribninfood.adaptor.ShowCategoryRecipeAdapter
import com.example.maribninfood.adaptor.SubCategoryAdapter
import com.example.maribninfood.dao.CategoryDao
import com.example.maribninfood.dao.RecipeDao
import com.example.maribninfood.dao.SaveDao
import com.example.maribninfood.databinding.ActivityCategoryDetailBinding
import com.example.maribninfood.databinding.ActivityDetailBinding
import com.example.maribninfood.databinding.ActivitySignupBinding
import com.example.maribninfood.databinding.FragmentHomeBinding
import com.example.maribninfood.model.Categories
import com.example.maribninfood.model.RecipeClass

class CategoryDetail : AppCompatActivity() {
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var binding: ActivityCategoryDetailBinding
    private lateinit var myAdapterCategory: ShowCategoryRecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val getData = intent.getStringExtra("category")
        recyclerViewCategory = binding.categoryDetailCard
        recyclerViewCategory.layoutManager = LinearLayoutManager(this)

        RecipeDao.getRecipeByCategory(getData!!){
            myAdapterCategory = ShowCategoryRecipeAdapter(it,R.layout.category_detail_card)
            recyclerViewCategory.adapter = myAdapterCategory
            myAdapterCategory.onItemClick = {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("android", it.id)
                startActivity(intent)
            }
            //back arrow
            myAdapterCategory.onBackArrowClick = {
                onBackPressed()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }
}

