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

}

fun main () {

    val secretNumber = SecretNumber()
    println(secretNumber.secret)
    println("${secretNumber.validate(2)}, count: ${secretNumber.count}")

}