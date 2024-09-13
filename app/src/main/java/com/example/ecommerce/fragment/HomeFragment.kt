package com.example.ecommerce.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.adapter.CategoryAdapter
import com.example.ecommerce.adapter.ProductAdapter
import com.example.ecommerce.databinding.FragmentHomeBinding
import com.example.ecommerce.model.AddProductModel
import com.example.ecommerce.model.categoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
        private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding= FragmentHomeBinding.inflate(layoutInflater)

        val preference=requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        if(preference.getBoolean("isCart",false))
            findNavController().navigate(R.id.action_homeFragment_to_cardFragment)

        getSliderImg()
        getCategories()
        getProducts()
        return binding.root
    }

    private fun getSliderImg() {
        Firebase.firestore.collection("slider").document("item")
            .get().addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImg)
            }
    }

    private fun getProducts() {
        val list=ArrayList<AddProductModel>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data=doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                binding.productRecycler.adapter= ProductAdapter(requireContext(),list)
            }
    }

    private fun getCategories() {
        val list=ArrayList<categoryModel>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data=doc.toObject(categoryModel::class.java)
                    list.add(data!!)
                }
                binding.categoryRecycler.adapter= CategoryAdapter(requireContext(),list)
            }
    }

}