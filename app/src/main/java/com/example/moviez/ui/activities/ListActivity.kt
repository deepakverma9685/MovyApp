package com.example.moviez.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviez.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
    }

    fun functionC(){
        System.out.print("Helo motu")
    }
}