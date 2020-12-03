package adventofcode.y2020

import adventofcode.readInput

open class Policy (val min:Int, val max:Int, val char:Char) {
    fun validate(word: String): Boolean {
        val count = word.count { it == char }
        return (count in min..max)
    }

    private fun check1(word: String): Boolean {
        if (min-1 < word.length) {
            return (word[min-1] == char)
        }
        return false
    }

    private fun check2(word: String): Boolean {
        if (max-1 < word.length) {
            return (word[max-1] == char)
        }
        return false
    }

    fun validate2(word: String): Boolean {
        if (check1(word) && !check2(word)) return true
        if (!check1(word) && check2(word)) return true
        return false
    }
}

fun createPolicy(input: String): Policy {
    val (min, max, char) = input.split("-", " ")
    return Policy(min.toInt(), max.toInt(), char[0])
}

data class Password (val policy: Policy, val word:String ) {
    fun validate(): Boolean = policy.validate(word)
    fun validate2(): Boolean = policy.validate2(word)
}

fun createPassword(input: String): Password {
    val (policyStr, word) = input.split(":")

    val policy = createPolicy(policyStr)

    return Password(policy, word.trimStart())
}

fun main() {
    val inputStrList = readInput(2, 2020)

    val inputList = inputStrList.map { createPassword(it) }

    val count = inputList.count { it.validate() }

    val count2 = inputList.count { it.validate2() }

    println("valid passwords $count and $count2")
}