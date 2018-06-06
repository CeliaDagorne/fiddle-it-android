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



// TODO : IMPORTANT : faire le design du menu
// TODO : IMPORTANT stocker le level auquel l'utilisateur est rendu
// TODO : afficher l'image en grand quand on gagne // mettre autre chose qu'un toast quand on perd?
// TODO : incrémenter le nombre d'essai de chaque level et l'afficher dans la vue
// TODO : faire le petit écran "informations"


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
        val ctx = applicationContext
        val buttonContainer = findViewById(R.id.buttonContainer) as FlexboxLayout
        val img = findViewById(R.id.imageView2) as ImageView
        val level_view = findViewById(R.id.levelNumber) as TextView
        val user_word_view = findViewById(R.id.textView) as TextView
        val underscore_view = findViewById(R.id.underscore) as TextView

        // get words.xml array
        val wordArray = resources.getStringArray(R.array.words)
        var level = newLevel

        val nb_letters_to_show = 12
        var word_to_find = wordArray[level]
        var word_to_find_formatted = ""
        //var word_to_find_length = word_to_find.length
        var letters_to_show = word_to_find

        // on vide les inputs, boutons, et underscores
        var user_word = ""
        var underscores = ""
        user_word_view.text = user_word
        underscore_view.text = underscores
        level_view.text = (newLevel+1).toString()
        if ((buttonContainer).childCount > 0)
            (buttonContainer).removeAllViews()

        // afficher l'image correspondant au niveau
        var suffix = "_min"
        var id = ctx.getResources().getIdentifier("$word_to_find$suffix", "drawable", ctx.getApplicationInfo().packageName);
        img.setImageResource(id);

        // si la longueur du mot est inférieur au nombre de boutons à afficher,
        // on ajoute autant de lettres aléatoires qu'il faut pour avoir le nombre de lettres à
        // afficher
        if (word_to_find.length < nb_letters_to_show) {
            var nb_letters_to_add = nb_letters_to_show - word_to_find.length
            var letters = generateRandomChars(nb_letters_to_add)
            for (letter in letters) {
                letters_to_show = "$letters_to_show$letter"
            }
        }
        // on mélange les lettres
        letters_to_show = shuffle(letters_to_show)


        // on ajoute le nombre d'underscore du mot à trouver dans la vue
        var space = " "
        for (letter in word_to_find) {
            word_to_find_formatted += "$letter "
            underscores += "_ "
        }
        underscore_view.text = underscores
        //Toast.makeText(this@GameActivity, underscores, Toast.LENGTH_SHORT).show()

        // pour chaque lettre, on créé le bouton
        // on créer le listener qui va gérer le click
        // puis on l'ajoute dans la vue
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
                button.setAlpha(.5f)
                button.setEnabled(false)
                if (user_word.length < word_to_find_formatted.length) {
                    user_word += letter_btn_string+" "
                    // Toast.makeText(this@GameActivity, user_word_txt, Toast.LENGTH_SHORT).show()
                    user_word_view.text = user_word

                    if(user_word == word_to_find_formatted) {
                        Toast.makeText(this@GameActivity, "Bravo, c'était bien \"$word_to_find\" !", Toast.LENGTH_SHORT).show()
                        var id = ctx.getResources().getIdentifier(word_to_find, "drawable", ctx.getApplicationInfo().packageName);
                        img.setImageResource(id);

                        android.os.Handler().postDelayed(
                                { foundWord(level) },
                                2000)


                    } else if (user_word.length == word_to_find_formatted.length) {
                        Toast.makeText(this@GameActivity, "Nope, essaye encore !", Toast.LENGTH_SHORT).show()

                        android.os.Handler().postDelayed(
                                { toLevel(level) },
                                2000)
                    }

                } else {
                    //Toast.makeText(this@GameActivity, "too long", Toast.LENGTH_SHORT).show()
                }
            }

            buttonContainer.addView(button)

        }
    }

    // générer une string de lettre aléatoire d'une longueur donnée
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

    // mélanger aléatoirement les lettres d'une string donnée
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