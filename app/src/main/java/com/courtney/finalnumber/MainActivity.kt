package com.courtney.finalnumber

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(), AnkoLogger {

    val secretNumber = SecretNumber()
    private val TAG = MainActivity::class.java.simpleName
    var min = 1
    var max = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            AlertDialog.Builder(this)
                .setTitle("Restart")
                .setMessage("Restart?")
                .setPositiveButton("OK") { dialog, which ->
                    min = 1
                    max = 100
                    secretNumber.restart()
                    txt_count.text = secretNumber.count.toString()
                    txt_secret.text = secretNumber.secret.toString()
                    edt_secret.setText("")
                    Log.d(TAG, "secret: ${secretNumber.secret}")
                }
                .setNeutralButton("Cancel", null)
                .show()
        }

        txt_secret.text = secretNumber.secret.toString()
        info { "secret: ${secretNumber.secret}" }

        btn_guess.setOnClickListener {
            val n = edt_secret.text.toString().toInt()
            info { "n: $n" }
            check(n)
        }

    }

    private fun check(n: Int) {

        val diff = secretNumber.validate(n)
        var message = "Bingo! The number is ${secretNumber.secret}"

        if (n in min until max) {
            when {
                diff > 0 -> {
                    message = "Smaller! $min ~ $n"
                    max = n
                }

                diff < 0 -> {
                    message = "Bigger! $n ~ $max"
                    min = n
                }
            }

            txt_count.text = secretNumber.count.toString()

        } else {
            message = "You enter a wrong number! Please enter number in range $min ~ $max"
        }

        AlertDialog.Builder(this)
            .setTitle("Message")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()

        edt_secret.setText("")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
