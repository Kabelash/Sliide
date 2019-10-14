package com.kabelash.sliide

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kabelash.sliide.data.News
import kotlinx.android.synthetic.main.news_row.view.*
import com.squareup.picasso.Picasso

/*
* Created by Kabelash on 14.10.2019
* */

class MainAdapter(val news: News): RecyclerView.Adapter<CustomViewHolder>(){

    override fun getItemCount(): Int {
        return news.content.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowCell = layoutInflater.inflate(R.layout.news_row, parent, false)
        return CustomViewHolder(rowCell)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val fd1 = news.content[position].title
        val fd2 = news.content[position].images.mainImageThumbnail.shortURL
        holder.view.newsTitle.text = fd1.toString() //Setting news title

        //Used Picasso to set image url
        Picasso.get()
        .load(fd2)
        .resize(50, 50)
        .centerCrop()
        .into(holder.view.newsImg)

    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {


}