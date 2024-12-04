package adventofcode.y2024

import kotlin.math.abs

class Day01(inputData: List<String>) {
    fun distance(i: Int): Int {
        return abs(list1[i] - list2[i])
    }

    fun allDistance(): Long = list1.zip(list2).sumOf { entry -> abs(entry.first.toLong() - entry.second) }

    fun countOf(i: Int) = groupCount.getOrDefault(i, 0)
    fun getSimilarityCount(): Long = list1.sumOf { key -> key.toLong() * countOf(key) }

    val list1: List<Int> = inputData.map { line -> line.split(" +".toRegex()).first().toInt() }.sorted()
    val list2: List<Int> = inputData.map { line -> line.split(" +".toRegex()).drop(1).first().toInt() }.sorted()

    private val groupCount: Map<Int, Int> = list2.groupBy { it }.mapValues { entry -> entry.value.size }
}
