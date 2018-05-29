package net.hetic.pixelchallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import android.graphics.Color
import android.R.attr.button
import android.support.design.widget.TabLayout.GRAVITY_CENTER
import java.util.Random


// TODO : changer le LinearLayout en un truc qui permet de wrap quand y'a trop de lettres
// TODO : shuffle les button avant de les ajouter a la vue
// TODO : ajouter des lettres en plus pour toujours en avoir un certain nombre
// (à définir mais genre 12?)

class GameActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val buttonContainer = findViewById(R.id.buttonContainer) as LinearLayout

        // val letter_btn = findViewById(R.id.letterBtn) as Button
        // val letter_btn_2 = findViewById(R.id.letterBtn2) as Button
        val user_word_view = findViewById(R.id.textView) as TextView
        val underscore_view = findViewById(R.id.underscore) as TextView


        val word_to_find = "cow"
        var word_to_find_formatted = ""
        val word_to_find_length = (word_to_find.length * 2 ) - 1

        val arrayy = word_to_find.toCharArray()
        print("Hello")

        fun <T> Array<T>.shuffle(): Array<T> {
            val rng = Random()

            for (index in 0..this.size - 1) {
                val randomIndex = rng.nextInt(this.size)

                // Swap with the random position
                val temp = this[index]
                this[index] = this[randomIndex]
                this[randomIndex] = temp
            }

            return this
        }

        // val wooord = arrayy.shuffle()



        var user_word = ""
        var underscores = ""
        for (letter in word_to_find) {
            word_to_find_formatted += "$letter "
            underscores += "_ "

            val button = Button(this)
            val scale = resources.displayMetrics.density
            val dpWidthInPx = (40 * scale).toInt()
            val dpHeightInPx = (40 * scale).toInt()
            val dpMarginInPx = (8 * scale).toInt()

            button.text = "$letter"
            button.setBackgroundResource(R.drawable.cta_bg)
            button.setTextColor(Color.WHITE)
            button.setGravity(GRAVITY_CENTER)
            button.layoutParams = LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx)
            val param = button.layoutParams as LinearLayout.LayoutParams
            param.setMargins(dpMarginInPx,dpMarginInPx,dpMarginInPx,dpMarginInPx);
            button.layoutParams = param

            // TODO : ajouter un eventListener sur le click de tous les boutons,
            // qui récupère la valeur cliquée et check si le mot est terminé ou pas

            button.setOnClickListener {
                val letter_btn_string = button.text.toString()
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

            buttonContainer.addView(button)


        }
        underscore_view.text = underscores


    }

}