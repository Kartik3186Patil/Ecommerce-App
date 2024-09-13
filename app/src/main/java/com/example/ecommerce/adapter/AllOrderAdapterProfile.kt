package com.example.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet.GONE
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.databinding.AllOrderItemLayoutProfileBinding
import com.example.ecommerce.model.AllOrderModelProfile

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllOrderAdapterProfile(private val list:ArrayList<AllOrderModelProfile>, val context:Context)
    : RecyclerView.Adapter<AllOrderAdapterProfile.AllOrderViewHolder>() {

    inner class AllOrderViewHolder(val binding:AllOrderItemLayoutProfileBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
       return AllOrderViewHolder(
           AllOrderItemLayoutProfileBinding.inflate(LayoutInflater.from(parent.context),parent,false)

       )
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.productTitle.text=list[position].name
        holder.binding.productPrice.text=list[position].price


        when(list[position].status){
            "Ordered"->{
               holder.binding.productStatus.text="Ordered"
            }
            "Dispatched"->{
                holder.binding.productStatus.text="Dispatched"
            }
            "Delivered"->{
                holder.binding.productStatus.text="Delivered"
            }
            "Cancelled"->{
                holder.binding.productStatus.text="Cancelled"
            }

        }
    }


}