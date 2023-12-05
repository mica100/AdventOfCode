package adventofcode.y2023

import adventofcode.readInput
import adventofcode.readInputData
import org.junit.Assert.assertEquals
import org.junit.Test

class Day03Test {

    val inputData = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...${'$'}.*....
            .664.598..
        """.trimIndent()

    @Test
    fun testDay03basics() {
        val subject = Day03(readInputData(inputData))
        val numberPos = subject.parseData()
        assertEquals(8, numberPos.size)

        val sum = subject.getPuzzle1Sum()
        assertEquals(4361, sum)
    }

    @Test
    fun testPuzzle1Solution() {
        val inputList = readInput(3, 2023)
        val subject = Day03(inputList)
        val sum = subject.getPuzzle1Sum()
        assertEquals(528799, sum)
    }

    @Test
    fun testGetNumberRange() {
        val subject = Day03()

        val numberRange = subject.getNumberRange("467..114..")
        val expected = listOf(
            Pair(467, 0..2), Pair(114, 5..7),
        )
        assertEquals(expected, numberRange)
    }

    @Test
    fun testGetSymbolPos() {
        val subject = Day03()

        val symbolPos = subject.getSymbolPos("...*...a.?")
        val expected = listOf(
            3, 9,
        )
        assertEquals(expected, symbolPos)
    }

    @Test
    fun testIsAdjacentNumber() {
        val subject = Day03()

        val validNumbers = subject.getNumbersFromLine2(listOf(
            "..........",
            "467..114..",
            "...*......",
        ))

        assertEquals(listOf(467), validNumbers)
    }

    @Test
    fun testFindAllAdjacentNumber() {
        val subject = Day03(readInputData(inputData))

        val validNumbers = subject.getNumbersFromLine2(subject.paddedList)

        assertEquals(listOf(467), validNumbers)
    }

    @Test
    fun testDay03parseStars() {
        val subject = Day03(readInputData(inputData))
        val sum = subject.getPuzzle2Sum()
        assertEquals(3, subject.starPosMap.size)
        assertEquals(2, subject.getFilteredStarList().size)
        assertEquals(467835, sum)
    }

    @Test
    fun testPuzzle2Solution() {
        val inputList = readInput(3, 2023)
        val subject = Day03(inputList)
        val sum = subject.getPuzzle2Sum()
        assertEquals(84907174, sum)
    }

}