package adventofcode.y2021

import adventofcode.readInput
import kotlin.system.exitProcess

object Day02 {
    fun getExampleData(): List<String> {
        return listOf<String>(
                "forward 5",
                "down 5",
                "forward 8",
                "up 3",
                "down 8",
                "forward 2",
        )
    }
}

class MoveCmd(val direction: String, val value: Int) {
    fun move(pos: Pair<Int, Int>): Pair<Int, Int> {
        return when (direction) {
            "forward" -> Pair(pos.first + value, pos.second)
            "down" -> Pair(pos.first, pos.second  + value)
            "up" -> Pair(pos.first, pos.second  - value)
            else -> throw IllegalArgumentException("Unknown direction $direction")
        }
    }

    fun move2(pos: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
        val aim = pos.third
        return when (direction) {
            "forward" -> Triple(pos.first + value, pos.second + value*aim, aim)
            "down" -> Triple(pos.first, pos.second, aim + value)
            "up" -> Triple(pos.first, pos.second, aim - value)
            else -> throw IllegalArgumentException("Unknown direction $direction")
        }
    }

}

fun main(args: Array<String>) {
    val inputValues = Day02.getExampleData()
    //val inputValues = readInput(2, 2021)
    //println(inputValues)

    val moves = inputValues.map { line -> line.split(" ").let { MoveCmd(it[0], it[1].toInt()) } }
    val endPos = moves.fold(Pair(0,0)) { currentPos, moveCmd -> moveCmd.move(currentPos) }
    println("Part1: endPos: $endPos => ${endPos.first * endPos.second}")

    val endPos2 = moves.fold(Triple(0,0,0)) { currentPos, moveCmd2 -> moveCmd2.move2(currentPos)}
    println("Part2: endPos2: $endPos2 => ${endPos2.first * endPos2.second}")
}

