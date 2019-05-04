package com.courtney.finalnumber

import android.content.Context
import android.content.Context.MODE_PRIVATE
import kotlin.random.Random

class SecretNumber(private val context: Context) {


    var max: Int = 100                                                      // 最大值
    var min: Int = 1                                                        // 最小值
    var secret: Int = Random.nextInt((max-min) + 1) + min             // 秘密數字
    var counter = 0                                                         // 猜的次數
    var wrong: Int = 0                                                      // 錯誤次數
    private var best = -1                                                   // 獲取最佳紀錄
    var bestDisplay: String = ""
        get() {
            best = context.getSharedPreferences("guess", MODE_PRIVATE)
                .getInt("BEST", -1)
            field = if (best == -1) context.getString(R.string.no_record) else best.toString()
            return field
        }

    // 判斷輸入之數字 & 判定是否為最佳紀錄
    var number: Int = 0
        set(value) {
            counter++
            when {
                value > secret -> max = value
                value < secret -> min = value
                else -> {
                    if (counter < best || best == -1) {
                        context.getSharedPreferences("guess", MODE_PRIVATE)
                            .edit()
                            .putInt("BEST", counter)
                            .apply()
                    }
                }
            }
            field = value
        }

    // 計算數字之差
    fun different(number: Int) : Int {
        return number - secret
    }

    // 重玩
    fun restart() {
        secret = Random.nextInt((max-min) + 1) + min
        wrong = 0
        counter = 0
    }

    fun isMatch(): Boolean {
        return number == secret
    }
}


/*if (number in min until max + 1) {
                message = when {
                    value > secret -> context.getString(R.string.smaller, min.toString(), number.toString())
                    value < secret -> context.getString(R.string.bigger, number.toString(), max.toString())
                    value == secret && counter < 3 -> {
                        if (best == -1 || counter < best) {
                            context.getSharedPreferences("guess", MODE_PRIVATE)
                                .edit()
                                .putInt("BEST", counter)
                                .apply()
                        }
                        context.getString(R.string.excellent_the_number_is, secret.toString(), counter.toString())
                    }
                    else -> {
                        if (best == -1 || counter < best) {
                            context.getSharedPreferences("guess", MODE_PRIVATE)
                                .edit()
                                .putInt("BEST", counter)
                                .apply()
                        }
                        context.getString(R.string.bingo_the_number_is, secret.toString(), counter.toString())
                    }
                }

                hint = when {
                    value > secret -> {
                        max = number
                        context.getString(R.string.to, min.toString(), number.toString())
                    }
                    value < secret -> {
                        min = number
                        context.getString(R.string.to, number.toString(), max.toString())
                    }
                    else -> {
                        min = 1
                        max = 100
                        context.getString(R.string.to, min.toString(), max.toString())
                    }
                }
            } else {
                wrong++
                Log.d(TAG, "wrong: $wrong")
                message = when {
                    wrong > 3 -> context.getString(R.string.play_the_game)
                    else ->  context.getString(R.string.you_enter_a_wrong_number_Please_enter_number_in_range, min.toString(), max.toString())
                }
            }*/