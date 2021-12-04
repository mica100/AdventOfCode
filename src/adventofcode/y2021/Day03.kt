package adventofcode.y2021

import adventofcode.readInput
import kotlin.system.exitProcess

object Day03 {
    fun getExampleData(): List<String> {
        return """
                00100
                11110
                10110
                10111
                10101
                01111
                00111
                11100
                10000
                11001
                00010
                01010
        """.trimIndent().lines()
    }

    fun parseBit1(pos: Int, inputList: List<String>): Int {
        val ones = inputList.sumOf { it[pos].digitToInt() }
        //println("parseBit1: $pos, $ones, ${inputList.size}, ${ones >= inputList.size.toFloat() /2}")
        return if (ones >= inputList.size.toFloat() /2) 1 else 0
    }

    fun filterList(pos: Int, ch: Int, inputList: List<String>): List<String> {
        return inputList.filter { it -> it[pos].digitToInt() == ch }
    }

    fun findMostSignificant(inputList: List<String>) : String {
        val range = inputList[0].length
        var localList = inputList
        for (pos in 0..range) {
            val bit = parseBit1(pos, localList)
            localList = filterList(pos, bit, localList)
            //println("LocalList: $localList")
            if (localList.size <= 1) break
        }
        return localList.first()
    }

    fun findLeastSignificant(inputList: List<String>) : String {
        val range = inputList[0].length
        var localList = inputList
        for (pos in 0..range) {
            val bit = parseBit1(pos, localList)
            localList = filterList(pos, 1 - bit, localList)
            if (localList.size <= 1) break
        }
        return localList.first()
    }

    fun parseBits(counter: Array<Int>, input: String): Array<Int> {
        input.forEachIndexed { index, c -> counter[index] += c.digitToInt() }
        return counter
    }

    fun getMostSignificantBits(counter: List<Int>, numberOfElements: Int): String {
        val halfElements = numberOfElements / 2
        return counter.joinToString("", "", "") { count -> if (count >= halfElements) "1" else "0" }
    }

    fun getLeastSignificantBits(counter: List<Int>, numberOfElements: Int): String {
        val halfElements = numberOfElements / 2
        return counter.joinToString("", "", "")  { count -> if (count >= halfElements) "0" else "1" }
    }
}

fun String.binaryAsInt() = this.toInt(2)

fun main(args: Array<String>) {
    //val input = Day03.getExampleData()
    val input = readInput(3, 2021)
    //println(input)

    val countArray = Array<Int>(input[0].length) { 0 }

    val bits = input.fold(countArray) { acc, s -> Day03.parseBits(acc, s) }.toList()
    println("Bits: ${bits}, elements: ${input.size}")

    val gamma = Day03.getMostSignificantBits(bits, input.size)
    val epsilon = Day03.getLeastSignificantBits(bits, input.size)
    println("Part1: gamma: $gamma, ${gamma.toInt(2)}, epsilon: $epsilon, ${epsilon.toInt(2)} => ${gamma.toInt(2)*epsilon.toInt(2)}")

    val oxygen = Day03.findMostSignificant(input)
    val co2 = Day03.findLeastSignificant(input)
    println("Part2: oxygen: $oxygen, co2: $co2 => ${oxygen.toInt(2)*co2.toInt(2)}")
}

