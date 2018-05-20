package net.hetic.pixelchallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.view.View
import android.widget.Toast


class GameActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val letter_btn = findViewById(R.id.letterBtn) as Button
        val letter_btn_2 = findViewById(R.id.letterBtn2) as Button
        val user_word_view = findViewById(R.id.textView) as TextView
        val underscore_view = findViewById(R.id.underscore) as TextView

        val word_to_find = "ARARA"
        var word_to_find_formatted = ""
        val word_to_find_length = (word_to_find.length * 2 ) - 1


        var user_word = ""
        var underscores = ""
        for (letter in word_to_find) {
            word_to_find_formatted += "$letter "
            underscores += "_ "
        }
        underscore_view.text = underscores


        letter_btn.setOnClickListener {
            val letter_btn_string = letter_btn.text.toString()
            if (user_word.length < word_to_find_formatted.length) {
                user_word += letter_btn_string+" "
                // Toast.makeText(this@GameActivity, user_word_txt, Toast.LENGTH_SHORT).show()
                user_word_view.text = user_word

                if(user_word == word_to_find_formatted) {
                    Toast.makeText(this@GameActivity, "found $word_to_find", Toast.LENGTH_SHORT).show()
                }else if (user_word.length == word_to_find_formatted.length) {
                    Toast.makeText(this@GameActivity, "nope !", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this@GameActivity, "too long", Toast.LENGTH_SHORT).show()
            }

        }

        letter_btn_2.setOnClickListener {
            val letter_btn_string = letter_btn_2.text.toString()
            if (user_word.length < word_to_find_formatted.length) {
                user_word += letter_btn_string+" "
                // Toast.makeText(this@GameActivity, user_word_txt, Toast.LENGTH_SHORT).show()
                user_word_view.text = user_word

                if(user_word == word_to_find_formatted) {
                    Toast.makeText(this@GameActivity, "found $word_to_find", Toast.LENGTH_SHORT).show()
                }else if (user_word.length == word_to_find_formatted.length) {
                    Toast.makeText(this@GameActivity, "nope !", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this@GameActivity, "too long", Toast.LENGTH_SHORT).show()
            }
        }


    }

}
