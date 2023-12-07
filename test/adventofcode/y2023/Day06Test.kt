package adventofcode.y2023

import adventofcode.readInput
import adventofcode.readInputData
import org.junit.Assert.assertEquals
import org.junit.Test

class Day06Test {

    val inputData = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()

    @Test
    fun testParsing() {
        val subject = Day06(readInputData(inputData))
        assertEquals(3, subject.races.size)
        assertEquals(listOf(4, 8, 9), subject.races.map { it.getValidDistance() })

        assertEquals(288, subject.getSolution1())
    }

    @Test
    fun testRaceRule() {
        val race1 = RaceRule(0, 7, 9)
        assertEquals(6, race1.getDistance(1))
        assertEquals(10, race1.getDistance(5))

        assertEquals(4, race1.getValidDistance())

        val race2 = RaceRule(1, 15, 40)
        assertEquals(8, race2.getValidDistance())
    }

    @Test
    fun testPuzzle1() {
        val subject = Day06(readInput(6, 2023))
        assertEquals(861300, subject.getSolution1())
    }

    @Test
    fun testRaceRule2() {
        val subject = Day06(readInputData(inputData))
        assertEquals(71503, subject.getSolution2())
    }

    @Test
    fun testPuzzle2() {
        val subject = Day06(readInput(6, 2023))
        assertEquals(28101347, subject.getSolution2())
    }

}