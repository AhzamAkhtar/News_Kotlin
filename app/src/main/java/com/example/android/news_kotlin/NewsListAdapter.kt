package com.example.android.news_kotlin

import android.content.Context
import android.os.Build.VERSION_CODES.N
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener: NewsItemClicked, val viewModel: NoteViewModel): RecyclerView.Adapter<NewsViewHolder>(){
    //val model = ViewModelProvider(Context)[]
     //lateinit var  viewModel: NoteViewModel




    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val viewHolder = NewsViewHolder(view)
        //viewModel = ViewModelProvider().get(NoteViewModel::class.java)
                //ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])

        }



        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
        //Toast.makeText(require(),"create",Toast.LENGTH_LONG).show()

      //viewModel  = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
      holder.addbutton.setOnClickListener {
           viewModel.insertNote(Note("bla"))
           viewModel.insertNote(Note(currentItem.title))


       }




    }

    fun updateNews(updatedNews: ArrayList<News>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
    val addbutton = itemView.findViewById<ImageView>(R.id.favButton)
}

interface NewsItemClicked {
    fun onItemClicked(item: News)
}