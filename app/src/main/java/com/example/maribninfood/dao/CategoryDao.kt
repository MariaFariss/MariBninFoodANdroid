package com.example.maribninfood.dao

import android.util.Log
import com.example.maribninfood.model.Categories
import com.google.firebase.firestore.FirebaseFirestore

object CategoryDao {
    private const val TAG  = "RecipeDao"
    fun getCategories(collection: String, callback: (ArrayList<Categories>) -> Unit){
        val listCategory = ArrayList<Categories>()
        FirebaseFirestore.getInstance().collection(collection)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listCategory.add(
                        Categories(
                            document.data["type"] as String,
                            document.reference.path,
                            document.data["image"] as String
                        )
                    )
                    Log.d(TAG,"listdatafor   "+listCategory)

                }
                Log.d(TAG,"listdata   "+listCategory)
                callback(listCategory)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}