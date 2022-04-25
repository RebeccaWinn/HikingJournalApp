package com.example.rebecca.hikingjournal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

private const val TAG= "MainActivity"

class MainActivity : AppCompatActivity(),
        HikeListFragment.Callbacks{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentFragment=
            supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null){
            val fragment= HikeListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container,fragment)
                .commit()
        }
    }

    override fun onHikeSelected(hikeId: UUID){
        val fragment = HikeFragment.newInstance(hikeId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .addToBackStack(null)
            .commit()
    }
}