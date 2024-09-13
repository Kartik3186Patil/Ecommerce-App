package com.example.ecommerce.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ActivityRegisterBinding
import com.example.ecommerce.model.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoginRegister.setOnClickListener{
            openLogin()
        }
        binding.buttonSignUpRegister.setOnClickListener{
            validateUser()
        }
    }

    private fun validateUser() {
        if(binding.userNameRegister.text!!.isEmpty()|| binding.userPhoneNumberRegister.text!!.isEmpty()){
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
        }else{
            storeData()
        }
    }



    private fun storeData() {
        val builder=AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        builder.show()
        
        val preferences=this.getSharedPreferences("user", MODE_PRIVATE)
        val editor=preferences.edit()

        editor.putString("number",binding.userPhoneNumberRegister.text.toString())
        editor.putString("name" ,binding.userNameRegister.text.toString())
        editor.apply()

        val data=UserModel(userName = binding.userNameRegister.text.toString(), userPhoneNumber =binding.userPhoneNumberRegister.text.toString() )

        Firebase.firestore.collection("users").document(binding.userPhoneNumberRegister.text.toString())
            .set(data).addOnSuccessListener {
                Toast.makeText(this,"User Registered",Toast.LENGTH_SHORT).show()
                builder.dismiss()
                openLogin()
            }
            .addOnFailureListener {
                builder.dismiss()
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            }
    }

    private fun openLogin() {
        binding.buttonSignUpRegister.setOnClickListener{
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}