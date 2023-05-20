package com.example.maribninfood.dao

import android.util.Log
import com.example.maribninfood.model.SaveRecipe
import com.google.firebase.firestore.FirebaseFirestore

object SaveDao {

    private const val COLLECTION  = "SaveRecipe"
    val db = FirebaseFirestore.getInstance()
    //function qui check si un une recette est deja sauvegarder dans firestore
    fun isSaved(mail : String, refRecipe : String, onResultRetrieved : (Boolean, String) ->(Unit)){

        db.collection(COLLECTION)
            .whereEqualTo("refRecipe", db.document(refRecipe))
            .whereEqualTo("mail", mail)
            .get()
            .addOnSuccessListener { documents ->
                val documentExists = !documents.isEmpty
                if(documentExists) {
                    val refRecipeSaved = documents.documents[0].reference.path
                    onResultRetrieved(documentExists, refRecipeSaved)
                }
                else
                    onResultRetrieved(documentExists, null.toString())

            }
            .addOnFailureListener { exception ->
               Log.d("SaveDAo", "failure ")
            }
    }

    //save recipe
    fun saveRecipe(mail : String, refRecipe : String, onResult: () -> Unit){
        val recipeRef = db.document(refRecipe)
        val data = hashMapOf(
            "mail" to mail,
            "refRecipe" to recipeRef
        )
        db.collection(COLLECTION)
            .document()
            .set(data)
            .addOnSuccessListener {
                onResult()
            }
            .addOnFailureListener{
                Log.d("SaveRecipe", "failure ")
            }
    }

    //unsaveRecipe
//    fun unsaveRecipes(mail : String,refRecipe: String, onResult: () -> Unit){
//        db.collection(COLLECTION)
//            .whereEqualTo("refRecipe", db.collection(RecipeDao.COLLECTION)
//            .whereEqualTo("mail", mail)
//            .get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    document.reference.delete()
//
//                }
//                onResult()
//            }
//            .addOnFailureListener{
//                Log.d("UnsaveRecipes", "failure ")
//            })
//    }

    fun unsaveRecipe(documentId: String, onResult: () -> Unit){
            db.document(documentId)
            .delete()
            .addOnSuccessListener {
                onResult()
            }
            .addOnFailureListener{
                Log.d("UnsaveRecipe", "failure ")
            }
    }


}
