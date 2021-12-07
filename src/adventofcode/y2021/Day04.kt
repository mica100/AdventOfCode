package adventofcode.y2021

import adventofcode.readInput
import kotlin.system.exitProcess

class Board(val id: Int, val field: Array<Array<Int>>, numberMap: MutableMap<Int, MutableList<Board>>, val selectedNumbers: MutableSet<Int>) {

    override fun toString(): String {
        return field.fold("Board $id, ${field.size}x${field[0].size}\n") { acc, it -> acc + it.joinToString { str -> "$str" + if (str in selectedNumbers) "*" else "" } + "\n" }
    }

    fun hasBingo(): Boolean {
        val boardSize = field.size
        for (row in 0 until boardSize) {
            var hit = 0
            for (col in 0 until boardSize) {
                //println("hasBingo $row, $col, ${field.size}, ${field[row].size}")
                if (field[row][col] in selectedNumbers) hit++
                if (hit == boardSize) return true
            }
        }
        for (col in 0 until boardSize) {
            var hit = 0
            for (row in 0 until boardSize) {
                if (field[row][col] in selectedNumbers) hit++
                if (hit == boardSize) return true
            }
        }
        return false
    }

    fun sumUnselected(): Int {
        val boardSize = field.size
        var sum = 0
        for (col in 0 until boardSize) {
            for (row in 0 until boardSize) {
                val number = field[row][col]
                if (number in selectedNumbers) {
                    //ignore
                } else {
                    sum += number
                }
            }
        }
        return sum
    }
}

class BoardManager(input: List<String>) {
    val numberMap = mutableMapOf<Int, MutableList<Board>>()
    val selectedNumbers = mutableSetOf<Int>()
    val boards = parseBoards(input).toMutableList()

    private fun parseBoards(input: List<String>): List<Board> {
        val boards_ = mutableListOf<Board>()
        val rows = mutableListOf<Array<Int>>()
        var lineNr = 0
        var boardSize = 0
        var boardId = 0
        for(line in input) {
            lineNr++
            val numbers = line.trim().split(Regex("\\D+"))
            //println("Debug: $lineNr: $line -> ${numbers.size}")
            if (numbers.size > 1) {
                if (boardSize == 0) boardSize = numbers.size
                val intVals = numbers.map { it.toInt() }
                rows.add(intVals.toTypedArray())
            } else if (rows.size == boardSize) {
                boards_.add(Board(boardId++, rows.toTypedArray(), numberMap, selectedNumbers))
                rows.clear()
            } else {
                println("Parse Error at line $lineNr: $line")
                exitProcess(1)
            }
        }
        if (rows.size == boardSize) {
            boards_.add(Board(boardId++, rows.toTypedArray(), numberMap, selectedNumbers))
            rows.clear()
        }
        return boards_
    }

    fun runGame(input: List<Int>): Pair<Int, Board>? {
        selectedNumbers.clear()
        for (number in input) {
            println("check for $number")
            selectedNumbers.add(number)
            boards.forEach { board ->
                if (board.hasBingo()) return Pair(number,board)
            }
        }
        return null
    }

    fun runGame2(input: List<Int>): Pair<Int, Board?>? {
        selectedNumbers.clear()
        var lastWin: Pair<Int, Board?>? = null
        for (number in input) {
            println("check for $number")
            selectedNumbers.add(number)
            val found = boards.filter { it.hasBingo() }
            if (found.size > 0) {
                lastWin = Pair(number, found.first())
                println("remove board: $found")
                boards.removeAll(found)
                println("boards.size = ${boards.size}")
            }
            if (boards.isEmpty()) {
                break
            }
        }
        return lastWin
    }

    override fun toString(): String {
        return "BoardManager\n"+boards.toString()
    }
}

object Day04 {
    fun getExampleData(): List<String> {
        return """
            7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
            
            22 13 17 11  0
             8  2 23  4 24
            21  9 14 16  7
             6 10  3 18  5
             1 12 20 15 19
            
             3 15  0  2 22
             9 18 13 17  5
            19  8  7 25 23
            20 11 10 24  4
            14 21 16 12  6
            
            14 21 17 24  4
            10 16 15  9 19
            18  8 23 26 20
            22 11 13  6  5
             2  0 12  3  7
         """.trimIndent().lines()
    }

}

fun main(args: Array<String>) {
    //val input = Day04.getExampleData()
    val input = readInput(4, 2021)
    //println(input)
    val numbers = input[0].split(",").map { it.toInt() }

    val bm = BoardManager(input.drop(2))
    //println("Boards: $bm")

    val result = bm.runGame(numbers)
    println("Part1: Result: $result")
    if (result != null) {
        println("Win on board ${result.first} with sum ${result.second.sumUnselected()}*${result.first}=${result.second.sumUnselected() * result.first}")
    }

    val bm2 = BoardManager(input.drop(2))
    val result2 = bm2.runGame2(numbers)
    println("Part2: Result: $result2")
    if (result2 != null) {
        if (result2.second != null) {
            val sum = result2.second?.sumUnselected()!!
            println("Last board to win has id ${result2.second?.id}, Number was ${result2.first} with sum ${sum}*${result2.first}=${sum * result2.first}")
        }
    }
}


