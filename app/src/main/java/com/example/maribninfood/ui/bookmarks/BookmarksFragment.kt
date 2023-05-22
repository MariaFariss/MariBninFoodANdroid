package com.example.maribninfood.ui.bookmarks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maribninfood.DetailActivity
import com.example.maribninfood.MainActivity
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
            listRecipe.clear() // Clear the list before populating with saved recipes
            for (save in savedRecipes) {
                RecipeDao.getRecipeByRef(save.referenceRecipe) { recipe ->
                    listRecipe.add(recipe)
                    // Update the adapter once all recipes have been retrieved
                    if (listRecipe.size == savedRecipes.size) {
                        bookmarksAdapter = ShowCategoryRecipeAdapter(listRecipe, R.layout.bookmark_card)
                        bookmarksRecyclerView.adapter = bookmarksAdapter
                        updateUI(bookmarksRecyclerView, view.findViewById(R.id.empty_text_view))
                        // more info button
                        bookmarksAdapter.onItemClick = {
                            val intent = Intent(context, DetailActivity::class.java)
                            intent.putExtra("android", it.id)
                            startActivity(intent)
                            Log.d("Categorydetail", "detaileddd sub")
                        }
                        //delete button
                        bookmarksAdapter.onDeleteClick = {
                            SaveDao.getSavedRecipeByIdandUser(mail, it.id) { saveDelete ->
                                Log.d("bookmarkfragment ", "deletebookmark test " + saveDelete.idSave)
                                SaveDao.unsaveRecipe(saveDelete.idSave) {
                                    listRecipe.remove(it)
                                    bookmarksRecyclerView.adapter?.notifyDataSetChanged()
                                    updateUI(bookmarksRecyclerView, view.findViewById(R.id.empty_text_view))
                                }
                            }
                        }
                    }
                }
            }
            // Check if the savedRecipes list is empty and update UI accordingly
            if (savedRecipes.isEmpty()) {
                bookmarksAdapter = ShowCategoryRecipeAdapter(listRecipe, R.layout.bookmark_card)
                bookmarksRecyclerView.adapter = bookmarksAdapter
                updateUI(bookmarksRecyclerView, view.findViewById(R.id.empty_text_view))
            }
        }

        return view
    }



    private fun updateUI(recyclerView: RecyclerView, emptyTextView: TextView) {
        if (recyclerView.adapter?.itemCount == 0) {
            recyclerView.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
            emptyTextView.text = "Your bookmark is empty 😔 \n\n  Go to the Home page to add some recipes to your bookmarks! \n\n Let's bookmark some recipes!\uD83D\uDE00"
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
        }
    }
}
