package adventofcode.y2015

import adventofcode.readInput

fun cutInput(input: String): List<Int> = input.split('x').map { it.toInt() }

fun calcSize(input: String): Int {

    val (l, w, h) = cutInput(input).sorted()

    val slack = l * w
    val paperSize = 2*l*w + 2*w*h + 2*h*l

    return paperSize + slack
}

fun calcRibbon(input: String): Int {
    val (l, w, h) = cutInput(input).sorted()
    return 2*l + 2*w + l*w*h
}

fun main() {
    val inputStr = readInput(2, 2015)

    val paperSize = inputStr.map { calcSize(it) }.sum()
    val ribbon = inputStr.map { calcRibbon(it) }.sum()

    print("Paper size:$paperSize, ribbon:$ribbon")
}
