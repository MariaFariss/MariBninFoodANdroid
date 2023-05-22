package com.example.maribninfood.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
//import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maribninfood.CategoryDetail
import com.example.maribninfood.DetailActivity
import com.example.maribninfood.adaptor.MainCategoryAdapter
import com.example.maribninfood.adaptor.SubCategoryAdapter
import com.example.maribninfood.dao.CategoryDao
import com.example.maribninfood.dao.RecipeDao
import com.example.maribninfood.databinding.FragmentHomeBinding
import com.example.maribninfood.model.RecipeClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var recyclerViewNewRecipe: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var dataList: ArrayList<RecipeClass>
    private lateinit var dataListOfNewRecipe: ArrayList<RecipeClass>
    private lateinit var myAdapterNewRecipe: MainCategoryAdapter
    private lateinit var myAdapterCategory: SubCategoryAdapter
    private lateinit var searchView: SearchView
    private lateinit var searchList: ArrayList<RecipeClass>
    private var isRestoringViewState = false //pour recuperer la vue lorsque'on clique le dessu

    companion object {
        private const val TAG = "HomeFragment"
    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //binding pour les deux recyclerView et le search et les vues dans le xml
        recyclerViewNewRecipe = binding.rvMainCategory
        recyclerViewCategory = binding.rvSubCategory
        searchView = binding.searchView
        //on indique la forme dans laquelle on affichera notre recylcerview
        recyclerViewNewRecipe.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewNewRecipe.setHasFixedSize(true)
        recyclerViewCategory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.setHasFixedSize(true)

        dataList = arrayListOf<RecipeClass>()
        dataListOfNewRecipe = arrayListOf<RecipeClass>()
        searchList = arrayListOf<RecipeClass>()

        /////////////new recipes/////////////////
        //retrieve new recipes only from the database firestore
        RecipeDao.getNewRecipe("Recipe") { listData ->
            dataList = listData
            dataListOfNewRecipe.addAll(listData.filter { it.newRecipes })
            searchList.addAll(listData)
            myAdapterNewRecipe = MainCategoryAdapter(dataListOfNewRecipe)
            recyclerViewNewRecipe.adapter = myAdapterNewRecipe
            //transformer les datas d'une activité a une autre
            myAdapterNewRecipe.onItemClick = {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("android", it.id)
                startActivity(intent)
                Log.d("home", "detaileddd")
            }
        }

        ///////////////categories//////////////
        CategoryDao.getCategories("Categories") {
            myAdapterCategory = SubCategoryAdapter(it)
            recyclerViewCategory.adapter = myAdapterCategory
            myAdapterCategory.onItemClick = {
                val intent = Intent(context, CategoryDetail::class.java)
                intent.putExtra("category", it.documentReference)
                startActivity(intent)
                Log.d("home", "detaileddd sub")
            }
        }
        ////////////pour le search /////////////
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                searchList.clear()
//                val searchText = newText!!.toLowerCase(Locale.getDefault())
//                if (searchText.isNotEmpty()){
//                    dataList.forEach{
//                        if (it.dataTitle.toLowerCase(Locale.getDefault()).contains(searchText)) {
//                            searchList.add(it)
//                        }
//                    }
//                    recyclerViewNewRecipe.adapter!!.notifyDataSetChanged()
//                } else {
//                    searchList.clear()
//                    searchList.addAll(dataList)
//                    recyclerViewNewRecipe.adapter!!.notifyDataSetChanged()
//                }
//                return false

                if (!isRestoringViewState) {
                    searchList.clear()
                    val searchText = newText?.toLowerCase(Locale.getDefault()) ?: ""
                    if (searchText.isNotEmpty()) {
                        dataList.forEach {
                            if (it.dataTitle.toLowerCase(Locale.getDefault())
                                    .contains(searchText)
                            ) {
                                searchList.add(it)
                            }
                        }
                        recyclerViewNewRecipe.adapter?.notifyDataSetChanged()
                    } else {
                        searchList.addAll(dataList)
                        recyclerViewNewRecipe.adapter?.notifyDataSetChanged()
                    }
                }
                return false
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    //restore the home view
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        isRestoringViewState = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        isRestoringViewState = false
    }


}