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
import com.example.maribninfood.DetailActivity
import com.example.maribninfood.R
import com.example.maribninfood.adaptor.MainCategoryAdapter
import com.example.maribninfood.adaptor.SubCategoryAdapter
import com.example.maribninfood.databinding.FragmentHomeBinding
import com.example.maribninfood.model.RecipeClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import java.util.Locale
import com.google.firebase.firestore.EventListener



class HomeFragment : Fragment() {

    private lateinit var recyclerViewMain: RecyclerView
    private lateinit var recyclerViewSub: RecyclerView
    private lateinit var dataList: ArrayList<RecipeClass>
    private lateinit var myAdapterMain: MainCategoryAdapter
    private lateinit var myAdapterSub: SubCategoryAdapter
    private lateinit var searchView: SearchView
    private lateinit var searchList: ArrayList<RecipeClass>
    private lateinit var db : FirebaseFirestore
    companion object{
        private const val TAG  = "HomeFragment"
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

        //binding pour les deux recyclerView et le search
        recyclerViewMain = binding.rvMainCategory
        recyclerViewSub = binding.rvSubCategory
        searchView = binding.searchView

        recyclerViewMain.layoutManager = LinearLayoutManager(context)
        recyclerViewMain.setHasFixedSize(true)
        recyclerViewSub.layoutManager = LinearLayoutManager(context)
        recyclerViewSub.setHasFixedSize(true)

        dataList = arrayListOf<RecipeClass>()
        searchList = arrayListOf<RecipeClass>()

        readFromFirestore("Recipe") { listData ->
            // Handle listData
            searchList.addAll(listData)
            recyclerViewMain.adapter = MainCategoryAdapter(searchList)
        }

        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    dataList.forEach{
                        if (it.dataTitle.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            searchList.add(it)
                        }
                    }
                    recyclerViewMain.adapter!!.notifyDataSetChanged()
                } else {
                    searchList.clear()
                    searchList.addAll(dataList)
                    recyclerViewMain.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })


        myAdapterMain = MainCategoryAdapter(searchList)
        recyclerViewMain.adapter = myAdapterMain

        myAdapterMain.onItemClick = {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("android", it)
            startActivity(intent)
            Log.d("home", "detaileddd")
        }

        myAdapterSub = SubCategoryAdapter(searchList)
        recyclerViewSub.adapter = myAdapterSub
        myAdapterSub.onItemClick = {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("android", it)
            startActivity(intent)
            Log.d("home", "detaileddd sub")
        }

        val user = FirebaseAuth.getInstance().currentUser
        val displayName = user?.displayName
        if (displayName != null) {
            Log.d("test", displayName)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//   fun getData(){
//        searchList.addAll(dataList)
//        recyclerViewMain.adapter = MainCategoryAdapter(searchList)
//    }

    fun readFromFirestore(collection: String, callback: (ArrayList<RecipeClass>) -> Unit) {
        val listData = ArrayList<RecipeClass>()
        FirebaseFirestore.getInstance().collection(collection)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listData.add(
                        RecipeClass(
                            document.data["dataImage"] as String,
                            document.data["dataTitle"] as String,
                            document.data["dataDesc"] as String
                        )
                    )
                    Log.d(TAG,"listdatafor   "+listData)

                }
                Log.d(TAG,"listdata   "+listData)
                callback(listData)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}