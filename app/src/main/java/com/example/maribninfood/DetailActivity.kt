package com.example.maribninfood

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.maribninfood.dao.CategoryDao
import com.example.maribninfood.dao.RecipeDao
import com.example.maribninfood.dao.SaveDao
import com.example.maribninfood.model.RecipeClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val ref = intent.getStringExtra("android")
        RecipeDao.getRecipeByRef(ref!!) { ref->
            val detailTitle: TextView = findViewById(R.id.dishTitle)
            val detailDesc: TextView = findViewById(R.id.tvIngredients)
            val detailInstruction: TextView = findViewById(R.id.tvInstructions)
            val image : ImageView = findViewById(R.id.imgToolbarBtnBack)
            val saveButton : Button = findViewById(R.id.btnSave)
            val shareButton = findViewById<Button>(R.id.btnShare)

            //save ui du Button pour que  l'ui reste sauvegarder une fois sauvgarder
            val user = FirebaseAuth.getInstance().currentUser
            val mail = user?.email
            SaveDao.isSaved(mail!!,ref.id) {isSaved,referenceID ->
                changeUi(isSaved, saveButton)
            }

            detailTitle.text = ref.dataTitle
            detailDesc.text = ref.dataDesc
            detailInstruction.text = ref.instruction.split("STEP").joinToString("\n\n"){ "STEP$it" }
            Glide.with(App.applicationContext)
                .load(Uri.parse(ref.dataImage))
                .into(image)
            //save button
            saveButton.setOnClickListener(){
                SaveDao.isSaved(mail!!,ref.id) {isSaved,referenceID ->
                    if(isSaved){
                        SaveDao.unsaveRecipe(referenceID){
                            changeUi(!isSaved,saveButton)
                        }
                    }
                    else
                        SaveDao.saveRecipe(mail,ref.id){
                            changeUi(!isSaved,saveButton)
                        }
                }
            }

            //pour le partage
            shareButton.setOnClickListener {
                shareRecipe(ref)
            }
            //back arrow
            val backArrowButton: ImageView = findViewById(R.id.arrowBack)
            backArrowButton.setOnClickListener {
                val intent = Intent(this, CategoryDetail::class.java)
                startActivity(intent)
                Log.d("Detail Activity", "back to category detail")
            }
            //pour les calories
            val caloriesButton: TextView = findViewById(R.id.cal)
            caloriesButton.text = ref.calories + " cal"
            //pour le temps de preparation
            val prepButton: TextView = findViewById(R.id.tvTime)
            prepButton.text = ref.prep + " min"

        }

    }
    fun changeUi(isRecipeSaved: Boolean, saveButton: Button) {
        if (isRecipeSaved) {
            saveButton.text = "Unsave"
            saveButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_bookmark_24, 0, 0, 0)
        } else {
            saveButton.text = "Save"
            saveButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_bookmark_border_24, 0, 0, 0)
        }
    }

    fun shareRecipe(recipe: RecipeClass) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this recipe: ${recipe.dataTitle} - ${recipe.dataImage}")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}