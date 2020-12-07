package adventofcode

import java.io.File

fun readInput(day: Int, year: Int): List<String> = readInput(fileNameFor(day, year))!!

fun readInput(fileName: String): List<String>? {
    val file = File(fileName)
    file.exists() || return null
    return file.bufferedReader().useLines { lines -> lines.toList() }
}

fun pathNameForYear(year: Int) = "puzzles/$year"
fun fileNameFor(day: Int, year: Int) = "${pathNameForYear(year)}/day${"%02d".format(day)}.txt"