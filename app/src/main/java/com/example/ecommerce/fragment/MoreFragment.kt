package com.example.ecommerce.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.adapter.AllOrderAdapterProfile
import com.example.ecommerce.databinding.FragmentMoreBinding
import com.example.ecommerce.model.AllOrderModelProfile
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MoreFragment : Fragment() {

    private lateinit var binding:FragmentMoreBinding
    private lateinit var list:ArrayList<AllOrderModelProfile>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentMoreBinding.inflate(layoutInflater)
        list= ArrayList()

        val preferences=requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)



        Firebase.firestore.collection("allOrders")
            .whereEqualTo("userId",preferences.getString("number","")!!)
            .get().addOnSuccessListener {
            list.clear()
            for(doc in it){
                val data=doc.toObject(AllOrderModelProfile::class.java)
                list.add(data)
            }
            binding.recyclerViewProfile.adapter=AllOrderAdapterProfile(list,requireContext())
        }
        return binding.root
    }


}