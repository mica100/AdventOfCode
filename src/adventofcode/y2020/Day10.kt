package adventofcode.y2020

import adventofcode.readInput
import kotlin.system.exitProcess

fun findWays(voltageList: List<Int>) {
    val countPosibleWays = mutableMapOf<Int, Long>(Pair(0, 1L))

    // loop over adapters but starting at index 1
    voltageList.drop(1).forEach { adapter ->
        // sum the value of the 3 lower adapters (if existing)
        var sum = 0L
        for (i in 3 downTo 1) {
            sum += countPosibleWays.getOrDefault(adapter-i, 0)
        }
        countPosibleWays[adapter] = sum
    }

    val ways = countPosibleWays[voltageList.last()]
    println("possible ways are $ways")
}

fun main() {
    val voltageList = readInput(10, 2020).map { it.toInt() }.toMutableList()

    val deviceVoltage = 3 + (voltageList.max() ?: 0)

    voltageList.add(0)
    voltageList.add(deviceVoltage)

    val distanceList = mutableListOf<Int>()
    for (window in voltageList.sorted().windowed(2, 1, false)) {
        val distance = window[1] - window[0]
        distanceList.add(distance)
    }
    println(distanceList)

    val distance1 = distanceList.count { it == 1 }
    val distance3 = distanceList.count { it == 3 }

    println("found deviceVoltage $deviceVoltage, 1=$distance1, 3=$distance3, product=${distance1*distance3}")

    findWays(voltageList.sorted())
//    var sumOne = 0
//    val oneList = mutableListOf<Int>()
//    for (entry in distanceList) {
//        when (entry) {
//            1 -> sumOne++
//            3 -> { if (sumOne>1) oneList.add(sumOne); sumOne=0 }
//        }
//    }
//    println(oneList)
//
//    val multiplyList = oneList.map { entry ->
//        val mul3 = if (entry>=2) entry-2 else 0
//        val plus2 = if (entry>1) 2 else 0
//        mul3*3 + plus2
//    }
//    println(multiplyList)
//
//    val result = multiplyList.fold(1L) { acc, i -> acc*i }
//
//    println(result)

//    // solution from Olaf
//    val cache = mutableMapOf<Int, Long>()
//    val allRatings = (voltageList + listOf(0)).sorted()
//
//    fun countWays(inJolts: Int = 0): Long {
//        if (inJolts == deviceVoltage) return 1L
//        val fits = allRatings.filter { rating -> inJolts in rating - 3 until rating }
//        return fits.sumOf { cache.getOrPut(it) { countWays(it) } }
//    }
//
//    fun part2BruteForce() = countWays()

//    var sumOne = mutableListOf<Int>()
//    var multiplyList = mutableListOf<Int>()
//    var ones = 0
//    for (entry in distanceList) {
//        when (entry) {
//            1 -> {
//                if (ones == 2) {
//                    sumOne.add(ones+1) // 2 previous and this one
//                } else if (ones<2) {
//                    ones++
//                }
//            }
//            3 -> {
//                if (ones == 2) sumOne.add(ones)
//
//                // TODO calc add
//                val sum = sumOne.sum()
//                if (sum > 0) {
//                    println(sumOne)
//                    sumOne.clear()
//                    multiplyList.add(sum)
//                }
//                ones = 0
//            }
//        }
//    }
//    println(multiplyList)
}