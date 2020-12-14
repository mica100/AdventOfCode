package adventofcode.generic

import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    fun add(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }
    fun times(by: Int): Point {
        return Point(x * by, y * by)
    }
    infix fun distanceTo(other: Point): Int {
        return abs(x - other.x) + abs(y - other.y)
    }

    fun turnLeft(): Point {
        return Point(y * -1, x)
    }

    fun turnRight(): Point {
        return Point(y, x * -1)
    }
    companion object {
        val ORIGIN = Point(0, 0)
    }
}