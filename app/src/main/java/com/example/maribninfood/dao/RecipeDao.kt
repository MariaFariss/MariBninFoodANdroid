package com.example.maribninfood.dao

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.example.maribninfood.model.RecipeClass
import com.example.maribninfood.ui.home.HomeFragment
import com.google.firebase.firestore.FirebaseFirestore

object RecipeDao {

    private const val TAG  = "RecipeDao"
    public const val COLLECTION  = "Recipe"
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()


    fun getNewRecipe(collection: String, callback: (ArrayList<RecipeClass>) -> Unit) {
        val listData = ArrayList<RecipeClass>()
        FirebaseFirestore.getInstance().collection(collection)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listData.add(
                        RecipeClass(
                            document.reference.path,
                            document.data["dataImage"] as String,
                            document.data["dataTitle"] as String,
                            document.data["dataDesc"] as String,
                            document.data["newRecipes"] as Boolean,
                            "",
                            document.data["instruction"] as String,
                            document.data["calories"] as String,
                            document.data["prep"] as String
                        )
                    )

                }
                Log.d(TAG,"listdata   "+listData)
                callback(listData)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getRecipeByCategory(refCategory : String, callback: (ArrayList<RecipeClass>) -> Unit){
        val listData = ArrayList<RecipeClass>()
        db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION).whereEqualTo("category", db.document(refCategory))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listData.add(
                        RecipeClass(
                            document.reference.path,
                            document.data["dataImage"] as String,
                            document.data["dataTitle"] as String,
                            document.data["dataDesc"] as String,
                            document.data["newRecipes"] as Boolean,
                            "",
                            document.data["instruction"] as String,
                            document.data["calories"] as String,
                            document.data["prep"] as String

                        )
                    )

                }
                Log.d(TAG,"listdata   "+listData)
                callback(listData)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getRecipeByRef(refCategory : String, callback: (RecipeClass) -> Unit){
        val listData = ArrayList<RecipeClass>()
        db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION)
            db.document(refCategory)
                .get()
            .addOnSuccessListener { document ->
                callback(RecipeClass(
                    document.reference.path,
                    document.data?.get("dataImage") as String,
                    document.data!!["dataTitle"] as String,
                    document.data!!["dataDesc"] as String,
                    document.data!!["newRecipes"] as Boolean,
                    "",
                    document.data!!["instruction"] as String,
                    document.data!!["calories"] as String,
                    document.data!!["prep"] as String

                ))
            }

            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    //add recipe
    fun addRecipe(recipe: RecipeClass, onResult: () -> Unit) {
        val data = hashMapOf(
            "dataImage" to recipe.dataImage,
            "dataTitle" to recipe.dataTitle,
            "dataDesc" to recipe.dataDesc,
            "newRecipes" to recipe.newRecipes,
            "category" to db.document(recipe.category),
            "instruction" to recipe.instruction,
            "calories" to recipe.calories,
            "prep" to recipe.prep
        )
        db.collection(COLLECTION)
            .document()
            .set(data)
            .addOnSuccessListener {
                onResult()
            }
            .addOnFailureListener {
                Log.d("SaveRecipe", "failure ")
            }
    }
}


