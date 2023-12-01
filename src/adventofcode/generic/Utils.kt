package adventofcode

import java.io.File

fun readInput(day: Int, year: Int, suffix: String = ""): List<String> = readInput(fileNameFor(day, year, suffix))!!

fun readInput(fileName: String): List<String>? {
    val file = File(fileName)
    file.exists() || return null
    return file.bufferedReader().useLines { lines -> lines.toList() }
}

fun pathNameForYear(year: Int) = "puzzles/$year"
fun fileNameFor(day: Int, year: Int, suffix: String) = "${pathNameForYear(year)}/day${"%02d".format(day)}$suffix.txt"

fun readInputData(dataString: String): List<String> = dataString.split("\r?\n|\r".toRegex())
