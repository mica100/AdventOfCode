package adventofcode.y2020

import adventofcode.readInput

data class Row (val data:String) {
    object Companion {
        val rows = mutableListOf<Row>()

        fun create(input: String): Row {
            val row = Row(input)
            rows += row
            return row
        }
    }

    fun hasTree(idx: Int): Boolean {
        val len = data.length
        // Bsp. len = 10, idx = 5 => 5, idx = 15 => 5
        val mod = idx % len
        return (data[mod] == '#')
    }
}

fun runDown(right: Int, down: Int): Int {
    var col = 0
    var row = 0
    var count = 0

    while (row < Row.Companion.rows.size) {
        val r = Row.Companion.rows[row]
        if (r.hasTree(col)) {
            count++
        }
        col += right
        row += down
    }

    return count
}

fun main() {
    val inputStrList = readInput(3, 2020)

    inputStrList.map { Row.Companion.create(it) }

    val a = runDown(1, 1)
    val b = runDown(3, 1)
    val c = runDown(5, 1)
    val d = runDown(7, 1)
    val e = runDown(1,2)

    println("answer: count=$b")
    val prod: Long = a.toLong()*b*c*d*e
    println("answer2: a=$a, b=$b, c=$c, d=$d, e=$d, product=$prod")
}