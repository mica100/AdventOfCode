package adventofcode.y2024

typealias Level = Int
typealias Report = List<Level>

class Day02(inputData: List<String>) {
    fun safeReportCount(): Int = reports.count { report -> report.isSafe() }
    fun safeReportsWithDampener(): Int = reports.count { report -> report.isSafeWithDampener() }

    val reports: List<Report> = inputData.map { line -> line.split(" ").map { it.toInt() } }
}

private fun Report.isSafe(): Boolean =
    calcDiffs().let { diffs ->
        when {
            diffs.validDiffs() -> true
            else -> false
        }.also { println("${size}: $this -> ${diffs.size}: $diffs -> $it") }
    }

private fun Report.isSafeWithDampener(): Boolean =
    if (isSafe()) true else {
        IntRange(0, size - 1).forEach { index ->
            if (dropElement(index).isSafe()) {
                return true
            }
        }
        false
    }

fun <E> List<E>.dropElement(index: Int): List<E> {
    return take(index) + drop(index + 1)
}

private fun Report.calcDiffs() = this.windowed(2).map { level -> level[1] - level[0] }.let { diffs ->
    if (diffs.count { it > 0 } < diffs.count { it < 0 }) diffs.map { it * -1 } else diffs
}

private fun List<Int>.validDiffs(): Boolean = all { it in 1..3 } || all { it in -3..-1 }

private fun List<Int>.recalculateDiffs(): List<Int> =
    windowed(2, partialWindows = true)
        .map { diff ->
            when {
                diff[0] < -3 || diff[0] > 3 || diff[0] == 0 -> 0
                diff.size > 1 && diff[1] <= 0 -> diff[0] + diff[1]
                else -> diff[0]
            }
        }
        .filterNot { it <= 0 }
        .also { println("was: $this -> $it") }

// 3 5 20 7  8
//  2 15 -13 1
//  2  2  1

// -3 5 7 8 10
//   8 2 1 2


//  5 3 5 7  9 8
//  -2 2 2 2 -1