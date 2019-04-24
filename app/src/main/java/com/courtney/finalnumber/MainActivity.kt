package com.courtney.finalnumber

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.alert
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), AnkoLogger {

    val secretNumber = SecretNumber()
    var min = 1      // 最小之範圍
    var max = 100    // 最大之範圍

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // 重玩
        fab.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.restart))
                .setMessage(getString(R.string.are_you_sure_to_restart))
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    replay()
                }
                .show()
        }

        // 設定秘密數字在畫面上
        txt_secret.text = secretNumber.secret.toString()
        info { "secret: ${secretNumber.secret}" }

        // 猜數字
        btn_guess.setOnClickListener {
            val n = edt_secret.text.toString().toInt()
            info { "n: $n" }
            check(n)
        }

    }

    private fun replay() {
        min = 1
        max = 100
        secretNumber.restart()
        txt_count.text = secretNumber.count.toString()
        txt_secret.text = secretNumber.secret.toString()
        edt_secret.setText("")
        info { "secret: ${secretNumber.secret}" }
    }

    private fun check(n: Int) {

        val diff = secretNumber.validate(n)
        var message: String
        var hint = "1~100"

        txt_count.text = secretNumber.count.toString()

        if (n in min until max + 1) {
            when {
                // 三次內猜對
                diff == 0 && secretNumber.count < 3 -> {
                    message = "${getString(R.string.excellent_the_number_is)} ${secretNumber.secret}"

                    alert(message, getString(R.string.message)) {
                        positiveButton(getString(R.string.ok)) { replay() }
                    }.show()

                }
                // 猜的數字大於秘密數字
                diff > 0 -> {
                    message = "${getString(R.string.smaller)}  $min ${getString(R.string.to)} $n"
                    hint = "$min ${getString(R.string.to)} $n"
                    max = n
                    toast(message)
                }
                // 猜的數字小於秘密數字
                diff < 0 -> {
                    message = getString(R.string.bigger) + n + "\t" + getString(R.string.to) + "\t" + max
                    hint = "$n  ${getString(R.string.to)} $max"
                    min = n
                    toast(message)
                }
                // 超過三次猜對
                else -> {
                    message = "${getString(R.string.bingo_the_number_is)} ${secretNumber.secret}"

                    alert(message, getString(R.string.message)) {
                        positiveButton(getString(R.string.ok)) { replay() }
                    }.show()
                }
            }

            txt_hint.text = hint
            txt_count.text = secretNumber.count.toString()

        } else {
            message =
                "${getString(R.string.you_enter_a_wrong_number_Please_enter_number_in_range)} $min ${getString(R.string.to)} $max"
            toast(message)
        }

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
