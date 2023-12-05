package adventofcode.y2023

/**
 * Find the star.
 * Check the numbers for adjacent stars.
 * Put the number in a map with pos of star as key.
 */

typealias TextPos = Pair<Int, Int>

data class StarValues(val pos: TextPos) {
    val valueList = emptyMap<TextPos, Int>().toMutableMap()

    fun hasExactly2Values() = valueList.size == 2

    fun getGear() = valueList.values.fold(1) { acc, i -> acc * i }
}

fun StarValues.add(pos: TextPos, value: Int) {
    if (!valueList.containsKey(pos)) {
        valueList[pos] = value
    }
}

class Day03(inputList: List<String> = emptyList()) {
    val starPosMap = emptyMap<TextPos, StarValues>().toMutableMap()

    val paddedList: List<String> by lazy {
        val mList = inputList.toMutableList()
        mList.add(0, "".padEnd(inputList.first().length, '.'))
        mList
    }

    fun getNumberRange(line: String): List<Pair<Int, IntRange>> =
        Regex("\\d+").findAll(line).map { Pair(it.value.toInt(), it.range) }.toList()

    fun getSymbolPos(line: String): List<Int> =
        Regex("[^0-9.a-zA-Z]").findAll(line).map { it.range.first }.toList()

    fun getSymbolPos(windowList: List<String>): List<Int> =
        windowList.map { line -> getSymbolPos(line) }.flatten()

    fun getStarPos(line: String): List<Int> =
        Regex("\\*").findAll(line).map { it.range.first }.toList()

    fun parseData(): List<Int> {
        return paddedList.windowed(3, partialWindows = true).mapIndexed { idx, window ->
            //println("$idx: $window")
            val numberPos = getNumbersFromLine2(window)
            //println("nr: $numberPos")
            numberPos
        }.flatten()
    }

    fun getNumbersFromLine2(windowList: List<String>): List<Int> {
        val numberPos = if (windowList.size > 1) getNumberRange(windowList[1]) else emptyList()
        if (numberPos.isNotEmpty()) {
            val symbolPos = getSymbolPos(windowList.take(3))
            return numberPos.mapNotNull { (value, range) ->
                val fullRange = IntRange(maxOf(0, range.first - 1), range.last + 1)
                if (symbolPos.any { fullRange.contains(it) }) value else null
            }
        }

        return emptyList()
    }

    fun getPuzzle1Sum() = parseData().sum()

    fun getPuzzle2Sum(): Int {
        paddedList.windowed(3, partialWindows = true).forEachIndexed { idx, window ->
            val line = idx - 1
            parseNumbersFromLine2for2(line, window)
        }
        //println("Filtered starValues: " + starPosMap.values.filter { it.hasExactly2Values() })
        return getFilteredStarList().sumOf { it.getGear() }
    }

    fun getFilteredStarList() = starPosMap.values.filter { it.hasExactly2Values() }

    fun parseNumbersFromLine2for2(baseLineIdx: Int, windowList: List<String>) {
        val numberLineIdx = baseLineIdx + 1 // check always 2nd line in window
        val numberPos = if (windowList.size > 1) getNumberRange(windowList[1]) else emptyList()
        //println("numberPos $numberLineIdx: $numberPos")
        if (numberPos.isNotEmpty()) {
            numberPos.map { (value, range) ->
                val numberStartPos = range.first
                val numberTextPos = TextPos(numberLineIdx, numberStartPos)
                val fullRange = IntRange(maxOf(0, range.first - 1), range.last + 1)
                windowList.mapIndexed { idx: Int, line: String ->
                    val lineNr = baseLineIdx + idx
                    val starPosList = getStarPos(line)
                    //println("starPosList $lineNr: $starPosList")
                    starPosList.filter { starStartPos -> fullRange.contains(starStartPos) }
                        .forEach { posInLine ->
                            val textPos = TextPos(lineNr, posInLine)
                            //println("add at star $textPos -> $numberTextPos")
                            starPosMap.getOrPut(textPos) { StarValues(textPos) }.add(numberTextPos, value)
                        }
                }
            }
        }
    }


}