package net.hetic.pixelchallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.view.View
import android.widget.Toast
import android.graphics.Color
import android.R.attr.button
import android.support.design.widget.TabLayout.GRAVITY_CENTER
import java.util.Random
import java.util.Collections
import java.util.Arrays
import java.util.Arrays.asList
import com.google.android.flexbox.FlexboxLayout
import android.util.Log

import android.widget.ArrayAdapter
import android.widget.ListView


// TODO : changer le LinearLayout en un truc qui permet de wrap quand y'a trop de lettres

class GameActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val buttonContainer = findViewById(R.id.buttonContainer) as FlexboxLayout

        // val letter_btn = findViewById(R.id.letterBtn) as Button
        // val letter_btn_2 = findViewById(R.id.letterBtn2) as Button
        val user_word_view = findViewById(R.id.textView) as TextView
        val underscore_view = findViewById(R.id.underscore) as TextView


        var listitems: ListView?=null

        listitems = findViewById(R.id.listItems)


        // WORDS DB

        var level = R.array.level_2
        val wordArray = resources.getStringArray(level)



        for (word in wordArray) {
            Log.d("word: ", word)

        }

        //

        val nb_letters_to_show = 12
        val word_to_find = "maman"
        var word_to_find_formatted = ""
        val word_to_find_length = word_to_find.length
        var letters_to_show = word_to_find

        if (word_to_find.length < nb_letters_to_show) {
            var nb_letters_to_add = nb_letters_to_show - word_to_find.length
            var letters = generateRandomChars(nb_letters_to_add)
            for (letter in letters) {
                letters_to_show = "$letters_to_show$letter"
            }

        }

        letters_to_show = shuffle(letters_to_show)
        // Toast.makeText(this@GameActivity, letters_to_show, Toast.LENGTH_LONG).show()


        var user_word = ""
        var underscores = ""

        for (letter in word_to_find) {
            word_to_find_formatted += "$letter "
            underscores += "_ "
            underscore_view.text = underscores
        }

        for (letter in letters_to_show) {

            val button = Button(this)
            val scale = resources.displayMetrics.density
            val dpWidthInPx = (40 * scale).toInt()
            val dpHeightInPx = (40 * scale).toInt()
            val dpMarginInPx = (8 * scale).toInt()

            button.text = "$letter"
            button.setBackgroundResource(R.drawable.cta_bg)
            button.setTextColor(Color.WHITE)
            button.setGravity(GRAVITY_CENTER)
            button.layoutParams = FlexboxLayout.LayoutParams(dpWidthInPx, dpHeightInPx)
            val param = button.layoutParams as FlexboxLayout.LayoutParams
            param.setMargins(dpMarginInPx,dpMarginInPx,dpMarginInPx,dpMarginInPx);
            button.layoutParams = param

            button.setOnClickListener {
                val letter_btn_string = button.text.toString()
                if (user_word.length < word_to_find_formatted.length) {
                    user_word += letter_btn_string+" "
                    // Toast.makeText(this@GameActivity, user_word_txt, Toast.LENGTH_SHORT).show()
                    user_word_view.text = user_word

                    if(user_word == word_to_find_formatted) {
                        Toast.makeText(this@GameActivity, "found $word_to_find", Toast.LENGTH_SHORT).show()
                    } else if (user_word.length == word_to_find_formatted.length) {
                        Toast.makeText(this@GameActivity, "nope !", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this@GameActivity, "too long", Toast.LENGTH_SHORT).show()
                }
            }

            buttonContainer.addView(button)


        }


    }

    fun generateRandomChars(length: Int): String {
        var candidateChars = "abcdefghijklmnopqrstuvwxyz"
        val sb = StringBuilder()
        val random = Random()
        for (i in 0 until length) {
            sb.append(candidateChars[random.nextInt(candidateChars
                    .length)])
        }

        return sb.toString()
    }

    fun shuffle(input: String): String {
        val characters = ArrayList<Char>()
        for (c in input.toCharArray()) {
            characters.add(c)
        }
        val output = StringBuilder(input.length)
        while (characters.size != 0) {
            val randPicker = (Math.random() * characters.size).toInt()
            output.append(characters.removeAt(randPicker))
        }
        return output.toString()
    }
}