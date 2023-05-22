package com.example.maribninfood.ui.account

import android.content.Intent
import android.os.Bundle
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
import androidx.recyclerview.widget.RecyclerView
import com.example.maribninfood.LoginActivity
import com.example.maribninfood.MainActivity
import com.example.maribninfood.R
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {

    private lateinit var personalInfoButton: TextView
    private lateinit var myRecipesButton: TextView
    private lateinit var personalInfoLayout: LinearLayout
    private lateinit var myRecipesLayout: RecyclerView
//    private lateinit var text: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        personalInfoButton = view.findViewById(R.id.tv_personal_info_button)
        myRecipesButton = view.findViewById(R.id.tv_my_recipes_button)

        personalInfoLayout = view.findViewById(R.id.pesonelInfoLayout)
        myRecipesLayout = view.findViewById(R.id.myrecipes_recyclerview)
//        text = view.findViewById(R.id.textView)

        personalInfoButton.setOnClickListener {
            Log.d("AccountFragment", "personalInfoButton clicked")
            personalInfoLayout.visibility = View.VISIBLE
            myRecipesLayout.visibility = View.GONE
            personalInfoButton.setTextColor(resources.getColor(R.color.white))
            myRecipesButton.setTextColor(resources.getColor(R.color.grey))
//            text.visibility = View.GONE
        }

        myRecipesButton.setOnClickListener {
            Log.d("AccountFragment", "myRecipesButton clicked")
            personalInfoLayout.visibility = View.GONE
            myRecipesLayout.visibility = View.VISIBLE
            personalInfoButton.setTextColor(resources.getColor(R.color.grey))
            myRecipesButton.setTextColor(resources.getColor(R.color.white))
//            text.visibility = View.VISIBLE
        }

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
