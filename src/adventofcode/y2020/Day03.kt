package adventofcode.y2020

import adventofcode.readInput

data class Row (val data:String) {
    companion object {
        val ROWS = mutableListOf<Row>()

        fun create(input: String): Row {
            val row = Row(input)
            ROWS += row
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

//fun createRow(input: String): Row {
//    return Row(input)
//}

fun runDown(right: Int, down: Int): Int {
    var col = 0
    var row = 0
    var count = 0

    while (row < Row.ROWS.size) {
        val r = Row.ROWS[row]
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

    inputStrList.forEach { Row.create(it) }

    //val rowList = mutableListOf<Row>()
    //inputStrList.forEach { rowList.add(createRow(it)) }
    //val rowList = inputStrList.map { createRow(it) }


    val a = runDown(1, 1)
    val b = runDown(3, 1)
    val c = runDown(5, 1)
    val d = runDown(7, 1)
    val e = runDown(1,2)

    val prod: Long = 1L*a*b*c*d*e
    println("answer2: a=$a, b=$b, c=$c, d=$d, e=$d, product=$prod")
}