package net.hetic.pixelchallenge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.Toolbar
import android.view.Window
import android.view.Gravity
import android.view.WindowManager
import android.widget.*
import android.support.constraint.ConstraintLayout

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main)


        // get views to update
        val levelView = findViewById(R.id.levelNumber) as TextView
        val nbTriesView = findViewById(R.id.nbTries) as TextView
        val img = findViewById(R.id.imageView) as ImageView

        // get list of words, to update image
        val wordArray = resources.getStringArray(R.array.words)

        // get local storage
        val ctx = getApplicationContext()
        val pref = ctx.getSharedPreferences("Game", MODE_PRIVATE)

        // to empty local storage uncomment the 4 lines below then launch app, and then re-comment to start storing again
        //val editor = pref.edit()
        //editor.remove("currentLevel")
        //editor.remove("nbTries")
        //editor.commit()
        // end empty local storage

        // get the current level from preferences
        var level = pref.getInt("currentLevel", 0)
        // show image that is linked to current level, suffix with min to show the small image
        var suffix = "_min"
        var word_to_find = wordArray[level]
        var id = ctx.getResources().getIdentifier("$word_to_find$suffix", "drawable", ctx.getApplicationInfo().packageName);
        img.setImageResource(id)
        // show level in view, add 1 because the levels start from 0 in the code
        levelView.text = (level+1).toString()
        // show the number of tries
        val nbTries = pref.getInt("nbTries", 0)
        nbTriesView.text = nbTries.toString()

        // add a click listener to start playing and launch game activity
        val btnOpenGameActivity : Button = findViewById(R.id.btn_open_game_activity)
        btnOpenGameActivity.setOnClickListener {
            val intent = Intent(this, GameActivity :: class.java)
            startActivity(intent)
        }

        val infoBtn : Button = findViewById(R.id.infoButton)
        val pageLayout : ConstraintLayout = findViewById(R.id.container)
        infoBtn.setOnClickListener {
            val window = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.popup, null)
            window.contentView = view


            val closeBtn = view.findViewById<ImageButton>(R.id.closeBtn)
            closeBtn.setOnClickListener {
                window.dismiss()
                pageLayout.setAlpha(1f)
            }
            window.showAtLocation(pageLayout, Gravity.CENTER,0,0);
            pageLayout.setAlpha(0.7f)
        }
    }
}
