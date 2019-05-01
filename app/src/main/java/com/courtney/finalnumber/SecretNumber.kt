package com.courtney.finalnumber

import android.content.Context
import android.content.Context.MODE_PRIVATE
import kotlin.random.Random

class SecretNumber(val context: Context) {


    var secret: Int = Random.nextInt(100) + 1  // 秘密數字
    var count = 0                                    // 猜的次數
    lateinit var message: String                     // 訊息
    lateinit var hint: String                        // 提示訊息
    var max: Int = 100                               // 最大值
    var min: Int = 0                                 // 最小值
    private var best = -1                            // 獲取最佳紀錄
    var bestDisplay: String = "No Record"
        get() {
            // TODO: 不會立即更新
            best = context.getSharedPreferences("guess", MODE_PRIVATE)
                .getInt("BEST", -1)
            field = if (best == -1) "No Record" else best.toString()
            return field
        }

    // 判斷輸入之數字 & 判定是否為最佳紀錄
    var number: Int = 0
        set(value) {
            count++
            field = value
            if (number in min until max + 1) {
                message = when {
                    value > secret -> context.getString(R.string.smaller, min.toString(), number.toString())
                    value < secret -> context.getString(R.string.bigger, number.toString(), max.toString())
                    value == secret && count < 3 -> context.getString(R.string.excellent_the_number_is, secret.toString())
                    else -> {
                        if (best == -1 || count < best) {
                            context.getSharedPreferences("guess", MODE_PRIVATE)
                                .edit()
                                .putInt("BEST", count)
                                .apply()
                        }
                        context.getString(R.string.bingo_the_number_is, secret.toString())
                    }
                }

                hint = when {
                    value > secret -> {
                        max = number
                        "$min ${context.getString(R.string.to)} $number"
                    }
                    value < secret -> {
                        min = number
                        "$number  ${context.getString(R.string.to)} $max"
                    }
                    else -> {
                        min = 1
                        max = 100
                        "$min  ${context.getString(R.string.to)} $max"
                    }
                }
            } else {
                message = "${context.getString(R.string.you_enter_a_wrong_number_Please_enter_number_in_range)} $min ${context.getString(R.string.to)} $max"
            }
        }

    // 計算數字之差
    fun different(number: Int) : Int {
        return number - secret
    }

    // 重玩
    fun restart() {
        secret = Random.nextInt(100) + 1
        count = 0
        hint = "1  ${context.getString(R.string.to)} 100"
    }
}