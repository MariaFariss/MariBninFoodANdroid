package com.example.maribninfood.ui.bookmarks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maribninfood.DetailActivity
import com.example.maribninfood.R
import com.example.maribninfood.adaptor.ShowCategoryRecipeAdapter
import com.example.maribninfood.dao.RecipeDao
import com.example.maribninfood.dao.SaveDao
import com.example.maribninfood.model.RecipeClass
import com.google.firebase.auth.FirebaseAuth

class BookmarksFragment : Fragment() {
    private lateinit var bookmarksAdapter: ShowCategoryRecipeAdapter
    private var listRecipe = ArrayList<RecipeClass>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookmarks, container, false)
        val bookmarksRecyclerView = view.findViewById<RecyclerView>(R.id.bookmarks_recycler_view)
        bookmarksRecyclerView.layoutManager = LinearLayoutManager(context)
        val user = FirebaseAuth.getInstance().currentUser
        val mail = user?.email
        SaveDao.getSavedRecipes(mail) { savedRecipes ->
            for (save in savedRecipes) {
                RecipeDao.getRecipeByRef(save.referenceRecipe) { recipe ->
                    listRecipe.add(recipe)
                    // Update the adapter once all recipes have been retrieved
                    if (listRecipe.size == savedRecipes.size) {
                        bookmarksAdapter = ShowCategoryRecipeAdapter(listRecipe,R.layout.bookmark_card)
                        bookmarksRecyclerView.adapter = bookmarksAdapter
                        /// le boutton more info
                        bookmarksAdapter.onItemClick = {
                            val intent = Intent(context, DetailActivity::class.java)
                            intent.putExtra("android", it.id)
                            startActivity(intent)
                            Log.d("Categorydetail", "detaileddd sub")
                        }
                        //le boutton delete
                        bookmarksAdapter.onDeleteClick = {
                            SaveDao.unsaveRecipe(save.idSave){
                                listRecipe.remove(it)
                                bookmarksRecyclerView.adapter?.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }

        return view
    }
}




        // Retrieve saved recipes from Firestore
//        val user = FirebaseAuth.getInstance().currentUser
//        val email = user?.email
//        SaveDao.getSavedRecipes(email) { savedRecipes ->
//            bookmarksList.clear()
//            bookmarksList.addAll(savedRecipes)
//            bookmarksAdapter.notifyDataSetChanged()
//        }

