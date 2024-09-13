package com.example.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.activity.CategoryActivity
import com.example.ecommerce.activity.ProductDetailsActivity
import com.example.ecommerce.databinding.LayoutCategoryItemBinding
import com.example.ecommerce.model.categoryModel


class CategoryAdapter(var context:Context, private val list: ArrayList<categoryModel>): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(view:View):RecyclerView.ViewHolder(view){
        var binding=LayoutCategoryItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
       return CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_category_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.textViewCategoryItem.text=list[position].cate
        Glide.with(context).load(list[position].img).into(holder.binding.imageView)

        holder.itemView.setOnClickListener{
            val intent= Intent(context,CategoryActivity::class.java)
            intent.putExtra("cate",list[position].cate)
            context.startActivity(intent)
        }


    }
}