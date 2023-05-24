package com.example.maribninfood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.maribninfood.dao.UserInfoDao
import com.example.maribninfood.ui.account.AccountFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [editUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class editUserFragment : Fragment() {
    private lateinit var password: TextView
    private lateinit var updatedPassword: TextView
//    private lateinit var mail: EditText
    private lateinit var pseudo: EditText
    private lateinit var editButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_user, container, false)
        password = view.findViewById(R.id.update_password)
        updatedPassword = view.findViewById(R.id.update_password_confirm)
//        mail = view.findViewById(R.id.update_email)
        pseudo = view.findViewById(R.id.update_pseudo)
        editButton = view.findViewById(R.id.edit_button)
        editButton.setOnClickListener{
            UserInfoDao.updateUsername(pseudo.text.toString()){
            Log.d("editfragemtn", "updateUsername: $it")
        }
//        UserInfoDao.updateEmail(mail.text.toString()){
//            Log.d("editfragemtn", "updateEmail: $it")
//        }
        UserInfoDao.updatePassword(password.text.toString()){
            if(password!=null && updatedPassword!=null){
                if(password.text.toString() == updatedPassword.text.toString()){
                    Log.d("editfragemtn", "updatePassword: $it")
                }
            }
        }
            findNavController().navigate(R.id.navigation_account)
        }

        return view
    }

}