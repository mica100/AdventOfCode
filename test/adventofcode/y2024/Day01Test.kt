package adventofcode.y2024

import adventofcode.readInput
import adventofcode.readInputData
import org.junit.Assert.assertEquals
import org.junit.Test

class Day01Test {

    private val testData = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.trimIndent()

    @Test
    fun `read in data to 2 lists`() {
        val day01 = Day01(readInputData(testData))
        assertEquals(6, day01.list1.size)
        assertEquals(6, day01.list2.size)
        assertEquals(1, day01.list1.first())
        assertEquals(3, day01.list2.first())

        assertEquals(2, day01.distance(0))
        assertEquals(11, day01.allDistance())
    }

    @Test
    fun `solve puzzle 1`() {
        val day01 = Day01(readInput(1, 2024))
        assertEquals(1882714, day01.allDistance())
    }

    @Test
    fun `similarity score example`() {
        val day01 = Day01(readInputData(testData))
        assertEquals(3, day01.countOf(3))
        assertEquals(31, day01.getSimilarityCount())
    }

        @Test
    fun `solve puzzle 2`() {
        val day01 = Day01(readInput(1, 2024))
        assertEquals(19437052, day01.getSimilarityCount())
    }

}