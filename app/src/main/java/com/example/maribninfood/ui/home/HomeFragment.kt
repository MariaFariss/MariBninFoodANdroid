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
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var recyclerViewMain: RecyclerView
    private lateinit var recyclerViewSub: RecyclerView
    private lateinit var dataList: ArrayList<RecipeClass>
    lateinit var imageList:Array<Int>
    lateinit var titleList:Array<String>
    lateinit var descList: Array<String>
    lateinit var detailImageList: Array<Int>
    private lateinit var myAdapterMain: MainCategoryAdapter
    private lateinit var myAdapterSub: SubCategoryAdapter
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

    private fun getData(){
        for (i in imageList.indices){
            val dataClass = RecipeClass(imageList[i], titleList[i], descList[i], detailImageList[i])
            dataList.add(dataClass)
        }
        searchList.addAll(dataList)
        recyclerViewMain.adapter = MainCategoryAdapter(searchList)
    }
}