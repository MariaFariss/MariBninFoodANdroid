package com.example.maribninfood.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
//import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maribninfood.DetailActivity
import com.example.maribninfood.R
import com.example.maribninfood.adaptor.MainCategoryAdapter
import com.example.maribninfood.dao.RecipeDao
import com.example.maribninfood.databinding.FragmentHomeBinding
import com.example.maribninfood.model.RecipeClass
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<RecipeClass>
    lateinit var imageList:Array<Int>
    lateinit var titleList:Array<String>
    lateinit var descList: Array<String>
    lateinit var detailImageList: Array<Int>
    private lateinit var myAdapter: MainCategoryAdapter
    private lateinit var searchView: SearchView
    private lateinit var searchList: ArrayList<RecipeClass>

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





        imageList = arrayOf(
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,

        )
        titleList = arrayOf(
            "ListView",
            "CheckBox",
            "ImageView",
            "Toggle Switch",
            "Date Picker")
        descList = arrayOf(
            getString(R.string.title_home),
            getString(R.string.title_home),
            getString(R.string.title_home),
            getString(R.string.title_home),
            getString(R.string.title_home)
        )
        detailImageList = arrayOf(
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img
        )
        recyclerView = binding.rvMainCategory
        searchView = binding.searchView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        dataList = arrayListOf<RecipeClass>()
        searchList = arrayListOf<RecipeClass>()
        getData()
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
                    recyclerView.adapter!!.notifyDataSetChanged()
                } else {
                    searchList.clear()
                    searchList.addAll(dataList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })


        myAdapter = MainCategoryAdapter(searchList)
        recyclerView.adapter = myAdapter

        myAdapter.onItemClick = {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("android", it)
            startActivity(intent)
            Log.d("home", "detaileddd")
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

    private fun getData(){
        for (i in imageList.indices){
            val dataClass = RecipeClass(imageList[i], titleList[i], descList[i], detailImageList[i])
            dataList.add(dataClass)
        }
        searchList.addAll(dataList)
        recyclerView.adapter = MainCategoryAdapter(searchList)
    }
}