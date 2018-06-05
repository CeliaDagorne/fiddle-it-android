package net.hetic.pixelchallenge

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




// TODO : stocker le level auquel l'utilisateur est rendu
// TODO : incrémenter le nombre d'essai de chaque level et l'afficher
// TODO : faire le menu

class GameActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        toLevel(0)

    }

    fun foundWord(currentLevel: Int){
        //user_word = ""
        //user_word_view.text = user_word
        toLevel(currentLevel + 1)
    }

    fun toLevel(newLevel: Int) {
        // TODO : réussir à récupérer la variable level
        val ctx = applicationContext

        val buttonContainer = findViewById(R.id.buttonContainer) as FlexboxLayout
        val img = findViewById(R.id.imageView2) as ImageView
        val user_word_view = findViewById(R.id.textView) as TextView
        val underscore_view = findViewById(R.id.underscore) as TextView

        // get words.xml array
        val wordArray = resources.getStringArray(R.array.words)
        var level = newLevel

        val nb_letters_to_show = 12
        var word_to_find = wordArray[level]
        var word_to_find_formatted = ""
        var word_to_find_length = word_to_find.length
        var letters_to_show = word_to_find

        var id = ctx.getResources().getIdentifier(word_to_find, "drawable", ctx.getApplicationInfo().packageName);
        img.setImageResource(id);

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
        // empty input
        user_word_view.text = user_word
        // empty button container
        if ((buttonContainer).childCount > 0)
            (buttonContainer).removeAllViews()

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
                        Toast.makeText(this@GameActivity, "Bravo, t'as trouvé !", Toast.LENGTH_SHORT).show()
                        foundWord(level)


                    } else if (user_word.length == word_to_find_formatted.length) {
                        Toast.makeText(this@GameActivity, "Nope, try again !", Toast.LENGTH_SHORT).show()
                        toLevel(level)
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