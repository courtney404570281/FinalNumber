package com.courtney.finalnumber

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class UserInfoActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val best = intent.getIntExtra("BEST", -1)
        info { "best: $best" }
    }
}
