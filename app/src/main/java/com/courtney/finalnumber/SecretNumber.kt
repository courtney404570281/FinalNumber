package com.courtney.finalnumber

import kotlin.random.Random

class SecretNumber {

    var secret = Random.nextInt(100) + 1
    var count = 0

    fun validate (number: Int): Int {
        count ++
        return number - secret
    }

    fun restart () {
        secret = Random.nextInt(100) + 1
        count = 0
    }

    fun getMessage (low: Int, high: Int, n: Int, diff: Int): String {
        var message :String
        var min = low
        var max = high

        if (n in min until max) {
            when {
                diff > 0 -> {
                    message = "Smaller! $min to $n"
                    max = n
                }

                diff < 0 -> {
                    message = "Bigger! $n to $max"
                    min = n
                }
                else -> {
                    message = "Bingo! The number is $secret"
                }
            }
        } else {
            message = "You enter a wrong number! Please enter a number in range $min to $max"
        }
        return message
    }

}

fun main () {

    val secretNumber = SecretNumber()
    println(secretNumber.secret)
    println("${secretNumber.validate(2)}, count: ${secretNumber.count}")

}