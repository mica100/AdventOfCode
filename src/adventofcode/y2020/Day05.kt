package adventofcode.y2020

import adventofcode.readInput

fun getCol(input: String): Int {
    val bin = input.replace('L', '0').replace('R', '1')
    val num = bin.toInt(2)
    return num
}

fun getRow(input: String): Int {
    val bin = input.replace('F', '0').replace('B', '1')
    val num = bin.toInt(2)
    return num
}

fun getSeat(input: String): Pair<Int, Int> {
    val row = getRow(input.substring(0..6))
    val col = getCol(input.substring(7..9))
    return Pair(col, row)
}

fun getSeatID(input: String): Int {
    val (col, row) = getSeat(input)

    return (row*8 + col)
}

fun main() {
    val maxSeatID = readInput(5, 2020).fold( 0 ) { acc, s -> maxOf(acc, getSeatID(s)) }

    // F = 0, B = 1


    println("answer: max=${maxSeatID}")

    val seatList = readInput(5, 2020).map { getSeatID(it) }

    var lastValue = -1
    for (v in seatList.sorted()) {
        if (v != lastValue+1) {
            if (lastValue+1 == v-1) {
                println("value is ${v-1}")
            }
        }

        lastValue = v
    }
}