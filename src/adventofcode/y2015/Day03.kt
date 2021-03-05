package adventofcode.y2015

import adventofcode.readInput

data class Point(val x: Int, val y: Int) {
    fun up(): Point = copy(y = y-1)
    fun down(): Point = copy(y = y+1)
    fun left(): Point = copy(x = x-1)
    fun right(): Point = copy(x = x+1)
}

//fun Char.operationFun(): (Point) -> Point = when(this) {
//    '^' -> Point::up
//    'v' -> Point::down
//    '>' -> Point::right
//    '<' -> Point::left
//    else -> error("invalid char $this")
//}

// extension property
val Char.operation: (Point) -> Point
    get() = when(this) {
        '^' -> Point::up
        'v' -> Point::down
        '>' -> Point::right
        '<' -> Point::left
        else -> error("invalid char $this")
    }

fun main() {
    val inputStr = readInput(3, 2015)

    val p = inputStr[0].fold( listOf(Point(0,0)) ) { acc, c -> acc + c.operation(acc.last()) }
    println(p.distinct().count())

    val pointList = mutableListOf(Point(0,0))
    var p1 = Point(0,0)
    var p2 = Point(0, 0)
    var idx = 0
    for (c in inputStr[0]) {
        val point = if (idx % 2 == 0) p1 else p2
        var point_new = point
        point_new = when(c) {
            '^' -> point.up()
            'v' -> point.down()
            '>' -> point.right()
            '<' -> point.left()
            else -> error("invalid char $c")
        }
        pointList.add(point_new)
        if (idx % 2 == 0) p1 = point_new else p2 = point_new

        idx++
    }
    val result = pointList.distinct().count()
    println("result $result")
}