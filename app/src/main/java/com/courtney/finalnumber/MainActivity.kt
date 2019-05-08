package com.courtney.finalnumber

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.alert

class MainActivity : AppCompatActivity(), AnkoLogger {

    var secretNumber = SecretNumber(this)
    companion object {
        val REQUEST_USERINFO: Int = 100
    }

    //TODO: 判斷邊界
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val intent = Intent(this, UserInfoActivity::class.java)
        startActivity(intent)

        fab.setOnClickListener {
            alert {
                title = getString(R.string.restart)
                message = getString(R.string.are_you_sure_to_restart)
                positiveButton(R.string.restart) {
                    replay()
                }
                negativeButton(R.string.cancel) {

                }
            }.show()
        }

        txt_secret.text = secretNumber.secret.toString()
        txt_best_record.text = secretNumber.bestDisplay

        btn_guess.setOnClickListener {
            secretNumber.number = edt_secret.text.toString().toInt()
            txt_secret.text = secretNumber.secret.toString()
            txt_count.text = secretNumber.counter.toString()
            // 更新範圍
            updateRange()
            isMatch()
            edt_secret.text = null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_USERINFO) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }
    }

    private fun isMatch() {
        if (secretNumber.isMatch()) {

            alert {
                title = getString(R.string.guess)
                message = getString(
                    R.string.bingo_the_number_is,
                    secretNumber.secret.toString(),
                    secretNumber.counter.toString()
                )
                positiveButton(R.string.restart) { replay() }
                negativeButton(R.string.cancel) {}
            }.show()
            txt_best_record.text = secretNumber.bestDisplay
            // 暫時註解
            // 移轉至 UserInfoActivity
            /*val intent = Intent(this, UserInfoActivity::class.java)
            intent.putExtra("BEST", secretNumber.counter)
            startActivityForResult(intent, REQUEST_USERINFO)*/
        }
    }

    // 更新範圍
    private fun updateRange() {
        txt_min.text = secretNumber.min.toString()
        txt_max.text = secretNumber.max.toString()
    }

    // 重玩
    private fun replay() {
        secretNumber.restart()
        updateRange()
        txt_secret.text = secretNumber.secret.toString()
        txt_count.text = secretNumber.counter.toString()
        edt_secret.text = null
        Snackbar.make(main , getString(R.string.the_game_has_restart), Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}


/*
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
            txt_hint.text = secretNumber.hint
            txt_count.text = secretNumber.counter.toString()
            // 清空欄位
            edt_secret.text = null
        }
    } else {
        txt_count.text = secretNumber.counter.toString()
        // 清空欄位
        edt_secret.text = null
        alert {
            title = getString(R.string.guess)
            message = secretNumber.message
            positiveButton(getString(R.string.ok)) {}
        }.show()
    }
}*/
