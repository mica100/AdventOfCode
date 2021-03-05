package adventofcode.y2020

import adventofcode.readInput

data class NumberInfo(val number: Int, var index: Int, var occurrence: Int = 1)

class MemoryGame(private val startNumbers: List<Int>) {
    private val numberMap = mutableMapOf<Int, NumberInfo>()
    private var lastNum = 0 // number a result of the last round

    fun nextRound(round: Int): Int {
        /*
        example: 0,3,6, 0,3,3,1,0,4
         */
        val lookUpEntry = numberMap[lastNum]
        // save lastNum
        if (round > 0)
            numberMap[lastNum] = NumberInfo(lastNum, round-1, 1 + (lookUpEntry?.occurrence ?: 0))

        if (lookUpEntry == null) { // not there yet
            lastNum = if (round < startNumbers.size)
                startNumbers[round]
            else
                0 // return 0
        } else {
            lastNum = round - 1 - lookUpEntry.index //calc distance
        }
        return lastNum
    }

    fun runGame(rounds: Int): Int {
        for(round in 0 until rounds) {
            nextRound(round)
        }
        return lastNum
    }

}

fun main() {
    val input = readInput(15, 2020)[0].split(',').map { it.toInt() }
    val game = MemoryGame(input)
    val lastNumber = game.runGame(2020)
    println(input + " give " + lastNumber)

    val game2 = MemoryGame(input)
    val lastNumber2 = game2.runGame(30000000)
    println(input + " give " + lastNumber2)
}