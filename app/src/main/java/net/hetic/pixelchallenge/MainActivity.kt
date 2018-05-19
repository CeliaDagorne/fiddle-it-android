package net.hetic.pixelchallenge

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenGameActivity : Button = findViewById(R.id.btn_open_game_activity)
        btnOpenGameActivity.setOnClickListener {
            val intent = Intent(this, GameActivity :: class.java)
            startActivity(intent)
        }
    }
}
