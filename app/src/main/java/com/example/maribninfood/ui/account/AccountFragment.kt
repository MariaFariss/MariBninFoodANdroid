package com.example.maribninfood.ui.account

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maribninfood.LoginActivity
import com.example.maribninfood.MainActivity
import com.example.maribninfood.R
import com.example.maribninfood.adaptor.ShowCategoryRecipeAdapter
import com.example.maribninfood.dao.RecipeDao
import com.example.maribninfood.dao.UserInfoDao
import com.example.maribninfood.editUserFragment
import com.example.maribninfood.model.RecipeClass
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {

    private lateinit var personalInfoButton: TextView
    private lateinit var myRecipesButton: TextView
    private lateinit var personalInfoLayout: LinearLayout
    private lateinit var myRecipesLayout: RecyclerView
    private lateinit var mail: TextView
    private lateinit var pseudo: TextView
    private lateinit var pseudoAdd: TextView
    private lateinit var editButton: ImageView
    private var listRecipe = ArrayList<RecipeClass>()
    private lateinit var userRecipeAdapter: ShowCategoryRecipeAdapter




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        //change the ui when the user click on the button
        personalInfoButton = view.findViewById(R.id.tv_personal_info_button)
        myRecipesButton = view.findViewById(R.id.tv_my_recipes_button)

        personalInfoLayout = view.findViewById(R.id.pesonelInfoLayout)
        myRecipesLayout = view.findViewById(R.id.myrecipes_recyclerview)

        personalInfoLayout.visibility = View.VISIBLE
        myRecipesLayout.visibility = View.GONE


        personalInfoButton.setOnClickListener {
            personalInfoLayout.visibility = View.VISIBLE
            myRecipesLayout.visibility = View.GONE
            personalInfoButton.setTextColor(resources.getColor(R.color.black))
            myRecipesButton.setTextColor(resources.getColor(R.color.lightGrey))

        }

        myRecipesButton.setOnClickListener {
            personalInfoLayout.visibility = View.GONE
            myRecipesLayout.visibility = View.VISIBLE
            personalInfoButton.setTextColor(resources.getColor(R.color.lightGrey))
            myRecipesButton.setTextColor(resources.getColor(R.color.black))

        }
        // personal info
        mail = view.findViewById(R.id.tv_mail)
        pseudo = view.findViewById(R.id.tv_pseudo)
        pseudoAdd = view.findViewById(R.id.tv_pseudoEdit)
        editButton = view.findViewById(R.id.edit)

        // get the user info
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val userInfo = UserInfoDao.getCurrentUser()
            val email = userInfo?.mail
            val name = userInfo?.pseudo
            mail.text = email
            pseudo.text = name
            pseudoAdd.text = name
        }

        // edit the user info when the user click on the button
        editButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_editPofile)
        }
        //show the user recipes
        val userRecipesRecyclerView = view.findViewById<RecyclerView>(R.id.myrecipes_recyclerview)
        userRecipesRecyclerView.layoutManager = LinearLayoutManager(context)
        val mail = user?.email
        RecipeDao.getRecipeByUser(mail){listRecipes ->
            for(recipe in listRecipes){
                RecipeDao.getRecipeByRef(recipe.refRecipe){recipe ->
                    listRecipe.add(recipe)
                    if (listRecipe.size == listRecipes.size){
                        userRecipeAdapter = ShowCategoryRecipeAdapter(listRecipe, R.layout.category_detail_card)
                        userRecipesRecyclerView.adapter = userRecipeAdapter
                    }
                }
            }
        }

        // logout
        val logoutButton: ImageView = view.findViewById(R.id.logoutButton)
        val goBack: ImageView = view.findViewById(R.id.arrowBack)
        logoutButton.setOnClickListener {
            logout()
        }

        goBack.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun logout() {
        // Logout the current user
        FirebaseAuth.getInstance().signOut()

        // Redirect to the login screen
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish() // Optional: Close the current activity after logout
    }
}
