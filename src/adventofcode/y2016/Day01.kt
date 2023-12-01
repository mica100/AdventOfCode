package adventofcode.y2016

import adventofcode.generic.Point

class Day01() {
    fun part1(instructions: List<Instruction>): Int {
        val origin = Point(0,0)
        return instructions.fold(FacingDirection.N to origin) {
            acc, i -> i.move(acc.first, acc.second)
        }.second distanceTo origin
    }

    fun part2(instructions: List<Instruction>): Int {
        val origin = Point(0,0)
        val visitedLocations = mutableSetOf<Point>()
        var currentPosition = origin
        var currentFacingDirection = FacingDirection.N
        for (instruction in instructions) {
            val p = instruction.moveDetailed(currentFacingDirection, currentPosition)
            currentPosition = p.second.last()
            currentFacingDirection = p.first
            for (tracePoint in p.second) {
                if (visitedLocations.contains(tracePoint)) {
                    currentPosition = tracePoint
                    break
                } else {
                    visitedLocations.add(tracePoint)
                }
            }
        }
        return currentPosition distanceTo origin
    }
}

data class Instruction(val dir: TurningDirection, val steps: Int) {
    companion object {
        fun create(input: String): Instruction {
            val dir = TurningDirection.valueOf(input[0].toString())
            val len = input.substring(1).toInt()

            return Instruction(dir, len)
        }

        fun listFromString(input: String): List<Instruction> {
            return input.split(',').map { create(it.trim()) }
        }
    }

    fun turn(currentFacingDirection: FacingDirection): FacingDirection {
        return when(dir) {
            TurningDirection.R -> FacingDirection.values()[(currentFacingDirection.ordinal + 1) % 4]
            TurningDirection.L -> FacingDirection.values()[(currentFacingDirection.ordinal - 1 + 4) % 4]
        }
    }

    fun move(facingDirection: FacingDirection, currentPosition: Point): Pair<FacingDirection, Point> {
        return turn(facingDirection).let { newDirection ->
            newDirection to currentPosition.next(newDirection, steps)
        }
    }

    fun moveDetailed(facingDirection: FacingDirection, currentPosition: Point): Pair<FacingDirection, List<Point>> {
        val newDirection = turn(facingDirection)
        val tracePoints = (1 until steps+1).map { step ->
            currentPosition.next(newDirection, step)
        }.toList()
        return newDirection to tracePoints
    }

}

enum class TurningDirection {
    R,
    L,
}
enum class FacingDirection {
    N, E, S, W
}

fun Point.next(movingDirection: FacingDirection, steps: Int): Point {
    return add(when(movingDirection) {
        FacingDirection.N -> Point(0, steps)
        FacingDirection.S -> Point(0, -steps)
        FacingDirection.E -> Point(steps, 0)
        FacingDirection.W -> Point(-steps, 0)
    })
}
