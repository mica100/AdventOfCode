package adventofcode.y2020

import adventofcode.readInput
import kotlin.math.min
import kotlin.math.max

class SeatMap(inputLines: List<String>) {

    val seatMap = mutableMapOf<Pair<Int, Int>, Char>()
    var seatMapRO = seatMap.toMap()
    val numRows = inputLines.size
    val numCols = inputLines[0].length

    init {
        inputLines.forEachIndexed { row, s ->
            s.forEachIndexed { col, c -> if (c == 'L') seatMap[Pair(col, row)] = 'L' }
        }
    }

    fun getNumOccupied(): Int {
        return seatMap.count { entry: Map.Entry<Pair<Int, Int>, Char> -> entry.value == '#' }
    }

    fun checkSeat(pos: Pair<Int, Int>, status: Char): Char {
        var newStatus = status

        var occupied = 0
        for (y in max(pos.second - 1,0) .. min(pos.second + 1,numRows-1) ) {
            for (x in max(pos.first -1,0) .. min(pos.first + 1,numCols-1) ) {
                if (x == pos.first && y == pos.second) continue
                when (seatMapRO[Pair(x,y)]) {
                    '#' -> occupied++
                }
            }
        }
        when (status) {
            'L' -> if (occupied == 0) newStatus = '#'
            '#' -> if (occupied >= 4) newStatus = 'L'
        }
        return newStatus
    }

    fun movePos(pos: Pair<Int, Int>, dir: String): Pair<Int, Int> {
        var x = pos.first
        var y = pos.second
        dir.forEach { c ->
            when (c) {
                'N' -> y--
                'S' -> y++
                'W' -> x--
                'E' -> x++
            }
        }
        return Pair(x, y)
    }

    fun findOccupiedSeat(pos: Pair<Int, Int>, dir: String): Boolean {
        var newPos = pos
        do {
            newPos = movePos(newPos, dir)
            when (seatMapRO.getOrDefault(newPos, '.')) {
                '#' -> return true
                'L' -> return false
            }
        } while (newPos.first in 0..numCols-1 && newPos.second in 0..numRows-1)
        return false
    }
    
    fun checkSeat2(pos: Pair<Int, Int>, status: Char): Char {
        var newStatus = status

        var occupied = 0

        val directions = listOf("N", "NE", "NW", "S", "SE", "SW", "E", "W")
        directions.forEach { dir -> if (findOccupiedSeat(pos, dir) ) occupied++ }

        when (status) {
            'L' -> if (occupied == 0) newStatus = '#'
            '#' -> if (occupied >= 5) newStatus = 'L'
        }
        return newStatus
    }

    fun runRound(): Int {
        // check each seat in RO map, write to seatMap
        var changes = 0
        seatMapRO = seatMap.toMap()
        seatMapRO.forEach { (pos, c) ->
            val oldStatus = c
            seatMap[pos] = checkSeat2(pos, c)
            if (oldStatus != seatMap[pos]) changes++
        }
        return changes
    }

    fun visualize() {
        for (y in 0 until numRows) {
            for (x in 0 until numCols) {
                print(seatMap.getOrDefault(Pair(x,y), "."))
            }
            println()
        }
    }
}

fun main() {
    val seatMap = SeatMap(readInput(11, 2020))

    //seatMap.visualize()
    var round = 0
    do {
        round++
        val changes = seatMap.runRound()
        println("round $round with $changes changes")
        //seatMap.visualize()
    } while (changes != 0)

    //seatMap.visualize()
    println("occupied seats ${seatMap.getNumOccupied()}")
}