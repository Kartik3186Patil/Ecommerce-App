package com.example.ecommerce.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecommerce.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddressBinding
    private lateinit var preferences:SharedPreferences
    private lateinit var totalCost:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences=this.getSharedPreferences("user", MODE_PRIVATE)

        totalCost=intent.getStringExtra("totalCost")!!

        loadUserInfO()
        binding.proceedCheckout.setOnClickListener{
            validateData(
                binding.UserNumber.text.toString(),
                binding.UserName.text.toString(),
                binding.UserPincode.text.toString(),
                binding.UserCity.text.toString(),
                binding.UserState.text.toString(),
                binding.UserVillage.text.toString(),
            )
        }
    }

    private fun validateData(
        number: String,
        name: String,
        pinCode: String,
        city: String,
        state: String,
        village: String
    ) {
        if( pinCode.isEmpty() || city.isEmpty() || state.isEmpty() || village.isEmpty()){
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
        }else{
            storeData(pinCode,city,state,village)
        }
    }

    private fun storeData( pinCode: String, city: String, state: String, village: String) {
        val map= hashMapOf<String,Any>()
        map["village"]=village
        map["state"]=state
        map["city"]=city
        map["pinCode"]=pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","111")!!)
            .update(map).addOnSuccessListener {

                val b=Bundle()
                b.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost",totalCost)
                val intent= Intent(this, CheckOutActivity::class.java)

                intent.putExtras(b)


                startActivity(intent)

            }
            .addOnFailureListener{
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfO() {


        Firebase.firestore.collection("users")
            .document(preferences.getString("number","111")!!)
            .get().addOnSuccessListener {
                binding.UserName.setText(it.getString("userName"))
                binding.UserNumber.setText(it.getString("userPhoneNumber"))
                binding.UserVillage.setText(it.getString("village"))
                binding.UserState.setText(it.getString("state"))
                binding.UserCity.setText(it.getString("city"))
                binding.UserPincode.setText(it.getString("pinCode"))
            }
            .addOnFailureListener{

            }
    }
}