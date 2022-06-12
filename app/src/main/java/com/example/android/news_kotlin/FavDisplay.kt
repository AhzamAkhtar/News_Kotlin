package com.example.android.news_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavDisplay: AppCompatActivity(), INOtesRVAdapter {
    lateinit var  viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.faf_layout)

        val recycleView: RecyclerView = findViewById(R.id.fav)
        recycleView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this,this)
        recycleView.adapter = adapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)


        viewModel.insertNote(Note("hello"))



        viewModel.allNotes.observe(this,
            Observer {list ->
                list?.let {
                    adapter.updateList(it)
                }
            })

    }

    override fun onItemClikced(note: Note) {
        viewModel.deleteNote(note)
    }

}