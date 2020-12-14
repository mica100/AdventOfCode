package adventofcode.y2020

import adventofcode.readInput
import adventofcode.generic.Point

class Waypoint(x: Int, y: Int) {
    var pos = Point(x, y)

    fun move(move: Direction): Point {
        when(move.dir) {
            'N' -> pos = pos.add(Point(0, move.len))
            'S' -> pos = pos.add(Point(0, -move.len))
            'E' -> pos = pos.add(Point(move.len, 0))
            'W' -> pos = pos.add(Point(-move.len, 0))
            'L' -> repeat(move.len / 90) { pos = pos.turnLeft() }
            'R' -> repeat(move.len / 90) { pos = pos.turnRight() }
            else -> throw IllegalArgumentException("No such direction ${move.dir}")
        }
        return pos
    }
}

class Ship2(x: Int, y: Int): Ship(x, y) {
    private val waypoint = Waypoint(10, 1)

    override fun drive(move: Direction): Point {
        when (move.dir) {
            'F' -> forward(move.len)
            else -> waypoint.move(move)
        }
        //println("move: ${move.dir}${move.len}, pos: $pos, waypoint: ${waypoint.pos}")
        return pos
    }

    override fun forward(len: Int) {
        val vector = waypoint.pos.times(len)
        pos = pos.add(vector)
    }
}

open class Ship(x: Int, y: Int) {

    open var pos = Point(x, y)
    private var headingDegree = 0
    var heading: Char = 'E'

    open fun drive(move: Direction): Point {
        when(move.dir) {
            'N' -> pos = pos.add(Point(0, move.len))
            'S' -> pos = pos.add(Point(0, -move.len))
            'E' -> pos = pos.add(Point(move.len, 0))
            'W' -> pos = pos.add(Point(-move.len, 0))
            'L' -> headingDegree += move.len
            'R' -> headingDegree -= move.len
            'F' -> forward(move.len)
            else -> throw IllegalArgumentException("No such direction ${move.dir}")
        }
        return pos
    }

    open fun forward(len: Int) {
        while (headingDegree<0) headingDegree += 360
        while (headingDegree>=360) headingDegree -= 360

        heading = when (headingDegree) {
            0 -> 'E'
            90 -> 'N'
            180 -> 'W'
            270 -> 'S'
            else -> throw IllegalArgumentException("No such turn $headingDegree")
        }
        drive(Direction(heading, len))
    }

    open fun getManhattanDistance(): Int {
        return Point.ORIGIN distanceTo pos
    }
}

data class Direction(val dir: Char, val len: Int) {
    companion object {
        fun create(input: String): Direction {
            val dir = input[0]
            val len = input.substring(1).toInt()

            return Direction(dir, len)
        }
    }
}


fun main() {
    val ship = Ship(0,0)

    val dirList = readInput(12, 2020).map { Direction.create(it) }

    dirList.forEach { ship.drive(it) }
    println("boat pos ${ship.pos.x}, ${ship.pos.y}, ${ship.heading}, distance: ${ship.getManhattanDistance()}")

    val ship2 = Ship2(0,0)
    dirList.forEach { ship2.drive(it) }
    println("boat pos ${ship2.pos.x}, ${ship2.pos.y}, distance: ${ship2.getManhattanDistance()}")

}