package net.hetic.pixelchallenge

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
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
import kotlin.math.log
import android.widget.LinearLayout
import android.R.id.edit
import android.content.SharedPreferences
import android.view.Window


// TODO : IMPORTANT : faire le design du menu
// TODO : faire le petit écran "informations"


class GameActivity : Activity()  {

    override fun onCreate(savedInstanceState: Bundle?) {

        // toolbar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        // get current level from preferences, if there are no level, return 0 and launch level 0 (first one)
        val ctx = getApplicationContext()
        val pref = ctx.getSharedPreferences("Game", MODE_PRIVATE)
        val level = pref.getInt("currentLevel", 0)
        toLevel(level)

    }

    // function to execute when user finds the right word,
    // sends to next level
    fun foundWord(currentLevel: Int){
        //user_word = ""
        //user_word_view.text = user_word
        toLevel(currentLevel + 1)
    }

    // function to contain all level activities
    fun toLevel(newLevel: Int) {
        // get views
        val buttonContainer = findViewById(R.id.buttonContainer) as FlexboxLayout
        val img = findViewById(R.id.imageView2) as ImageView
        val level_view = findViewById(R.id.levelNumber) as TextView
        val user_word_view = findViewById(R.id.textView) as TextView
        val underscore_view = findViewById(R.id.underscore) as TextView
        val nbTriesView = findViewById(R.id.nbTries) as TextView

        // put the current level in the preferences, to store it for app restart
        var level = newLevel
        val ctx = applicationContext
        val pref = ctx.getSharedPreferences("Game", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt("currentLevel", level)
        editor.commit()

        // set the number of buttons to show,
        // get the right word for the level
        val nb_letters_to_show = 12
        val wordArray = resources.getStringArray(R.array.words)
        var word_to_find = wordArray[level]
        var word_to_find_formatted = ""
        //var word_to_find_length = word_to_find.length
        var letters_to_show = word_to_find

        // reset input, buttons, underscores
        var user_word = ""
        var underscores = ""
        user_word_view.text = user_word
        underscore_view.text = underscores
        level_view.text = (newLevel+1).toString()
        val nbTries = pref.getInt("nbTries", 0)
        nbTriesView.text = nbTries.toString()
        if ((buttonContainer).childCount > 0)
            (buttonContainer).removeAllViews()

        // show level image
        var suffix = "_min"
        var id = ctx.getResources().getIdentifier("$word_to_find$suffix", "drawable", ctx.getApplicationInfo().packageName);
        img.setImageResource(id);

        // show enough random letters to fill the number of buttons needed
        if (word_to_find.length < nb_letters_to_show) {
            var nb_letters_to_add = nb_letters_to_show - word_to_find.length
            var letters = generateRandomChars(nb_letters_to_add)
            for (letter in letters) {
                letters_to_show = "$letters_to_show$letter"
            }
        }
        // shuffle the letters
        letters_to_show = shuffle(letters_to_show)


        // add the underscore to show the number of letters
        for (letter in word_to_find) {
            word_to_find_formatted += "$letter "
            underscores += "_ "
        }
        underscore_view.text = underscores

        // for each letter, create the button and add it to view, add event listener to handle click
        for (letter in letters_to_show) {

            // create button and add style
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

            // handle click event
            button.setOnClickListener {
                // get the letter of clicked button
                val letter_btn_string = button.text.toString()
                // disable the button
                button.setAlpha(.5f)
                button.setEnabled(false)
                // if the word the user typed in smaller to the word to find, add letter to user input
                if (user_word.length < word_to_find_formatted.length) {
                    // add letter to user input
                    user_word += letter_btn_string+" "
                    user_word_view.text = user_word

                    // if the user has found the word
                    if(user_word == word_to_find_formatted) {
                        // show "BRAVO" and reveal full image
                        Toast.makeText(this@GameActivity, "Bravo, c'était bien \"$word_to_find\" !", Toast.LENGTH_SHORT).show()
                        var id = ctx.getResources().getIdentifier(word_to_find, "drawable", ctx.getApplicationInfo().packageName);
                        img.setImageResource(id);

                        // add one try to local storage
                        val ctx = applicationContext
                        val pref = ctx.getSharedPreferences("Game", MODE_PRIVATE)
                        val editor = pref.edit()
                        val nbTries = pref.getInt("nbTries", 0)
                        editor.putInt("nbTries", nbTries+1)
                        editor.commit()

                        // launch foundWord function after 1s
                        android.os.Handler().postDelayed(
                                { foundWord(level) },
                                1000)


                    } else if (user_word.length == word_to_find_formatted.length) {
                        // if the word is the right length but not the word to find, the user loose
                        Toast.makeText(this@GameActivity, "Nope, essaye encore !", Toast.LENGTH_SHORT).show()

                        // add 1 try
                        val ctx = applicationContext
                        val pref = ctx.getSharedPreferences("Game", MODE_PRIVATE)
                        val editor = pref.edit()
                        val nbTries = pref.getInt("nbTries", 0)
                        editor.putInt("nbTries", nbTries+1)
                        editor.commit()

                        // relaunch same level after 1s
                        android.os.Handler().postDelayed(
                                { toLevel(level) },
                                1000)
                    }

                } else {
                    // if the number of letters to find is already filled and wrong, do nothing
                }
            }
            // add button to view
            buttonContainer.addView(button)

        }
    }

    // generate random string of given length
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

    // shuffle given string
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