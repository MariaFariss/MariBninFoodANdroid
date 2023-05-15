package com.example.maribninfood

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.example.maribninfood.databinding.ActivityDetailBinding
import com.example.maribninfood.model.RecipeClass

class DetailActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_detail)

            val getData = intent.getParcelableExtra<RecipeClass>("android")
            if (getData != null) {
                val detailTitle: TextView = findViewById(R.id.detailTitle)
                val detailDesc: TextView = findViewById(R.id.detailDesc)
                val image : ImageView = findViewById(R.id.detailImage)
//                val detailImage: ImageView = findViewById(R.id.detailImage)

                detailTitle.text = getData.dataTitle
                detailDesc.text = getData.dataDesc
                Glide.with(App.applicationContext)
                    .load(Uri.parse(getData.dataImage))
                    .into(image)
//                detailImage.setImageResource(getData.dataDetailImage)
            }
        }
    }
