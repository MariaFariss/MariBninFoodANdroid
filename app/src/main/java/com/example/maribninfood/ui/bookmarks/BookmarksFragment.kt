package com.example.maribninfood.ui.bookmarks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maribninfood.R
import com.example.maribninfood.adaptor.BookmarksAdaptor
import com.example.maribninfood.adaptor.ShowCategoryRecipeAdapter
import com.example.maribninfood.dao.RecipeDao
import com.example.maribninfood.dao.SaveDao
import com.example.maribninfood.databinding.FragmentBookmarksBinding
import com.example.maribninfood.model.RecipeClass
import com.example.maribninfood.model.SaveRecipe
import com.google.firebase.auth.FirebaseAuth

//
//class BookmarksFragment : Fragment() {
//
//    private var _binding: FragmentBookmarksBinding? = null
//    private lateinit var recyclerViewSave: RecyclerView
//    private lateinit var dataList: ArrayList<SaveDao>
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
////        val notificationsViewModel =
////            ViewModelProvider(this).get(NotificationsViewModel::class.java)
//
//        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.saveTitle
////        notificationsViewModel.text.observe(viewLifecycleOwner) {
////            textView.text = it
////        }
//        return root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}

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
                        bookmarksAdapter = ShowCategoryRecipeAdapter(listRecipe)
                        bookmarksRecyclerView.adapter = bookmarksAdapter
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

