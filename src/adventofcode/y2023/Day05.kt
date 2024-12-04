package adventofcode.y2023

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

data class CalcMap(val name: String, val from: String, val to: String) {
    val calc = mutableListOf<ConversionMap>()

    fun addConversion(line: String) {
        calc.add(createConversionMap(line))
    }

    fun convert(value: Long) = calc.fold(value) { acc, c ->
        c.convert(value) ?: acc
    }
}

class ConversionMap(val destStart: Long, source: Long, rangeLength: Long) {
    val sourceRange = source until source + rangeLength

    fun convert(value: Long) =
        if (sourceRange.contains(value)) value - sourceRange.first + destStart else null
}

fun createConversionMap(line: String): ConversionMap {
    // 50 98 2 => dest, source, length
    val (dest, source, length) = line.split(" ")
    return ConversionMap(dest.toLong(), source.toLong(), length.toLong())
}

class CalcMaps(val maps: MutableMap<String, CalcMap>) {
    val size: Int
        get() = maps.size

    val listTo: Map<String, String> = maps.values.associate { Pair(it.to, it.name) }
    val listFrom: Map<String, String> = maps.values.associate { Pair(it.from, it.name) }

    fun getConversionSteps(from: String = "location", to: String = "seed"): List<String> {
        val calcList = mutableListOf<String>()
        var lastElement: String? = from
        while (lastElement != null && to != lastElement) {
            val mapName = listTo.getOrDefault(lastElement, null)
            if (mapName != null) {
                calcList.add(mapName)
                lastElement = mapName.fromMapName().first
            } else lastElement = null
        }
        return calcList.reversed()
    }

    fun stepsAsMapList(steps: List<String>) = steps.mapNotNull { mapName -> maps[mapName] }

    fun convert(value: Long, steps: List<CalcMap>): Long =
        steps.fold(value) { acc, calcMap ->
            calcMap.convert(acc)
        }

    fun getMinOf(seeds: List<Long>, from: String = "location", to: String = "seed"): Pair<Long, Long> {
        val steps = getConversionSteps(from, to)
        val stepsAsList = stepsAsMapList(steps)
        val InOutPairs = seeds.map { Pair(it, convert(it, stepsAsList)) }
        return InOutPairs.minByOrNull { it.second }!!
    }

    fun getMinOfSeedRanges(seedRanges: List<Long>): Pair<Long,Long> {
        val steps = getConversionSteps()
        val stepsAsList = stepsAsMapList(steps)
        return runBlocking (Dispatchers.Default) {
            val all = seedRanges.chunked(2) { (start, length) ->
                async {
                    var minValue = Long.MAX_VALUE
                    var input = Long.MAX_VALUE
                    for (i in start until start + length) {
                        val a = convert(i, stepsAsList)
                        if (a < minValue) {
                            minValue = a
                            input = i
                        }
                    }
                    println("finished $start: $input -> $minValue")
                    input to minValue
                }
            }.awaitAll()
            all.minByOrNull { it.second }!!
        }
    }

}


fun Pair<String, String>.asMapName() = "${first}-to-${second}"
fun String.fromMapName() = split("-to-").let { Pair(it[0], it[1]) }

class Day05(val inputList: List<String>) {

    val seeds = mutableListOf<Long>()

    val calcMaps by lazy {
        val maps: MutableMap<String, CalcMap> = mutableMapOf()
        var currentMap: CalcMap? = null

        inputList.forEach { line ->
            if (line.endsWith(" map:")) {
                val name = line.substringBeforeLast(" map:")
                val (from, to) = name.split("-to-")
                currentMap = maps.getOrPut(name) { CalcMap(name, from, to) }
            } else if (line.startsWith("seeds: ")) {
                seeds.addAll(line.substringAfter("seeds: ").split(" ").map { it.toLong() })
            } else if (line.isNotBlank()) {
                currentMap?.addConversion(line)
            }
        }
        CalcMaps(maps)
    }

}
