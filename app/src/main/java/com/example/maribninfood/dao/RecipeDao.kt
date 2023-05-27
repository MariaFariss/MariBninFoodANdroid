package com.example.maribninfood.dao

import android.util.Log
import com.example.maribninfood.model.RecipeClass
import com.example.maribninfood.model.RecipesUser
import com.example.maribninfood.model.SaveRecipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

object RecipeDao {

    private const val TAG  = "RecipeDao"
    public const val COLLECTION  = "Recipe"
    private var DB : FirebaseFirestore = FirebaseFirestore.getInstance()


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
        DB = FirebaseFirestore.getInstance()
        DB.collection(COLLECTION).whereEqualTo("category", DB.document(refCategory))
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
        DB = FirebaseFirestore.getInstance()
        DB.collection(COLLECTION)
            DB.document(refCategory)
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
            "category" to DB.document(recipe.category),
            "instruction" to recipe.instruction,
            "calories" to recipe.calories,
            "prep" to recipe.prep
        )
        DB.collection(COLLECTION)
            .add(data)
            .addOnSuccessListener {docRef ->
                val email = FirebaseAuth.getInstance().currentUser?.email
                val data = hashMapOf(
                    "refRecipe" to docRef,
                    "mail" to email
                )
                DB.collection("RecipesUser")
                    .add(data)
                    .addOnSuccessListener {
                        onResult()
                    }
            }
            .addOnFailureListener {
                Log.d(TAG, "failure ")
            }
    }

    fun getRecipeByUser(email: String?, callback: (List<RecipesUser>) -> Unit) {
        if (email != null) {
            val userRecipes = ArrayList<RecipesUser>()
            SaveDao.db.collection("RecipesUser")
                .whereEqualTo("mail", email)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot) {
                        val recipeRef = document.data["refRecipe"] as DocumentReference
                        userRecipes.add(
                            RecipesUser(
                                email,
                                recipeRef.path
                            )
                        )

                    }
                    callback(userRecipes)
                }
        }

    }
}


