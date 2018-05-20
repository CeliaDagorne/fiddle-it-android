package net.hetic.pixelchallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast;


class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val letter_btn = findViewById(R.id.letterBtn) as Button
        val letter_btn_2 = findViewById(R.id.letterBtn2) as Button
        val user_word_view = findViewById(R.id.textView) as TextView
        var user_word = ""

        letter_btn.setOnClickListener {
            val letter_btn_string = letter_btn.text.toString()
            user_word += letter_btn_string
            val user_word_txt = user_word.toString()
            // Toast.makeText(this@GameActivity, user_word_txt, Toast.LENGTH_SHORT).show()
            user_word_view.text = user_word_txt
        }

        letter_btn_2.setOnClickListener {
            val letter_btn_string = letter_btn_2.text.toString()
            user_word += letter_btn_string
            val user_word_txt = user_word.toString()
            // Toast.makeText(this@GameActivity, user_word_txt, Toast.LENGTH_SHORT).show()
            user_word_view.text = user_word_txt
        }


    }
}
