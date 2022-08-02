package com.example.android.news_kotlin

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES.N
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.Model
import com.google.android.material.snackbar.Snackbar

class NewsListAdapter(private val listener: NewsItemClicked, val viewModel: NoteViewModel,val context: Context): RecyclerView.Adapter<NewsViewHolder>(){
    private val items: ArrayList<News> = ArrayList()
    //val model = ViewModelProvider(Context)[]
     //lateinit var  viewModel: NoteViewModel
     //val
    // adapter
    // adapter new



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

       val temp : News = News(currentItem.title,currentItem.author,currentItem.url,currentItem.imageUrl)
      //viewModel  = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
      holder.addbutton.setOnClickListener {
          //val snackbar : Snackbar = Snackbar.make(contex)
           viewModel.insertNote(Note(currentItem.title))
       }
        holder.delbutton.setOnClickListener{

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
            builder.setMessage("Are You Sure")
            builder.setIcon(R.drawable.ic_round_delete_24)

            builder.setPositiveButton("Yes"){dialogInterface,which ->
                items.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,items.size)
                val snackbar = Snackbar.make(it,"Item Deleted",Snackbar.LENGTH_LONG)
                snackbar.setAction("UNDO",View.OnClickListener {
                    Log.d("UNdoNew","undonew")
                    items.add(position,temp)
                    notifyItemInserted(position)
                    notifyItemRangeChanged(position,items.size)
                    val snackbar = Snackbar.make(it,"Item Added",Snackbar.LENGTH_LONG).show()
                })
                snackbar.show()
            }

            builder.setNegativeButton("No"){dialogInterface,which ->
                val snackbar = Snackbar.make(it,"Deletion Cancel",Snackbar.LENGTH_LONG).show()
            }

            val alertDialog:AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }
        holder.sharebutton.setOnClickListener{
           val sendIntent : Intent = Intent().apply {
               action = Intent.ACTION_SEND
               putExtra(Intent.EXTRA_TEXT,"Read This Article \n" + currentItem.title + "\n" + currentItem.url)
               type = "text/plain"
           }
            val shareIntent = Intent.createChooser(sendIntent,null)
            context.startActivity(shareIntent)

        }
    }


    /**class MyUndoListener : View.OnClickListener {

        override fun onClick(v: View) {
            // Code to undo the user's last action
            Log.d("undo","undo")
        }
    }**/
    /**private fun delete(position: Int,view: View){
        Log.d("dbharrynew","deleted")
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,items.size)
       val snackbar =  Snackbar.make(view,"Item Deleted",Snackbar.LENGTH_LONG)
        snackbar.setAction("UNDO",MyUndoListener())
        snackbar.show()
    }*/


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
    val delbutton : ImageView = itemView.findViewById(R.id.delButton)
    val sharebutton : ImageView = itemView.findViewById(R.id.shareButton)
}

interface NewsItemClicked {
    fun onItemClicked(item: News)
}