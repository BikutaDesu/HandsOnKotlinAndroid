package br.com.bikutadesu.handsonandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        supportActionBar!!.hide()
    }
}