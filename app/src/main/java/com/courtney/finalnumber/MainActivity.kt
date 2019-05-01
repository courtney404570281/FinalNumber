package com.courtney.finalnumber

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), AnkoLogger {

    var secretNumber = SecretNumber(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            replay()
        }

        txt_secret.text = secretNumber.secret.toString()
        txt_best_record.text = secretNumber.bestDisplay
        btn_guess.setOnClickListener {
            secretNumber.number = edt_secret.text.toString().toInt()
            if (secretNumber.number in secretNumber.min until secretNumber.max + 1) {
                if (secretNumber.different(secretNumber.number) == 0) {
                    alert {
                        title = getString(R.string.guess)
                        message = secretNumber.message
                        positiveButton(getString(R.string.ok)) {
                            replay()
                        }
                    }.show()
                } else {
                    edt_secret.text = null
                    txt_count.text = secretNumber.count.toString()
                    txt_hint.text = secretNumber.hint
                    toast(secretNumber.message)
                }
            } else {
                alert {
                    title = getString(R.string.guess)
                    txt_count.text = secretNumber.count.toString()
                    edt_secret.text = null
                    message = secretNumber.message
                    positiveButton(getString(R.string.ok)) {}
                }.show()
            }
        }
    }

    private fun replay() {
        secretNumber.restart()
        txt_secret.text = secretNumber.secret.toString()
        txt_count.text = secretNumber.count.toString()
        edt_secret.text = null
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
