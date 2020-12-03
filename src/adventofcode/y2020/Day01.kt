package adventofcode.y2020

import adventofcode.readInput
import kotlin.system.exitProcess

fun calc1(inputList: List<Int>) {
    for (v1 in inputList) {
        for (v2 in inputList) {
            if (v1 + v2 == 2020) {
                println("$v1 + $v2 = ${v1 + v2}, product is ${v1 * v2}")
                return
            }
        }
    }
    println("calc1: no result found!")
}

fun calc2(inputList: List<Int>) {

    for (a in inputList) {
        for (b in inputList) {
            if (a + b < 2020) {
                for (c in inputList) {
                    if (a + b + c == 2020) {
                        println("a,b,c is $a, $b, $c, product is ${a*b*c}")
                        return
                    }
                }
            }
        }
    }
    println("calc2: no result found!")
}

fun main() {
    val inputValues = readInput(1, 2020).map { it.toInt() }

    calc1(inputValues)

    calc2(inputValues)
}