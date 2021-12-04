package adventofcode.y2021

import adventofcode.readInput
import kotlin.system.exitProcess

fun List<Int>.countIncrements() = this.fold(Pair(0, 0)) { acc, i -> Pair(i, if (i > acc.first) acc.second+1 else acc.second) }.second.minus(1)

fun main(args: Array<String>) {
    //val inputValues = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)
    val inputValues = readInput(1, 2021).map { it.toInt() }
    //println(inputValues)

    val increments = inputValues.countIncrements()
    println("Part1: Increments: $increments")

    val input3sum = inputValues.windowed(3,1,false).map { it.sum() }
    println("List with 3: ${input3sum.countIncrements()}")
}

