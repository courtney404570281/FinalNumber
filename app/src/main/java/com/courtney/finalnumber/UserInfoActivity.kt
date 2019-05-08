package com.courtney.finalnumber

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.spinner

class UserInfoActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val best = intent.getIntExtra("BEST", -1)
        info { "best: $best" }
        val numbers = mutableListOf(16, 17, 18, 19)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, numbers)
        spinner().adapter = adapter
    }
}
