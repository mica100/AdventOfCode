package adventofcode.y2023

import adventofcode.readInput
import adventofcode.readInputData
import org.junit.Assert.assertEquals
import org.junit.Test

class Day08Test {

    val inputData = """
        RL

        AAA = (BBB, CCC)
        BBB = (DDD, EEE)
        CCC = (ZZZ, GGG)
        DDD = (DDD, DDD)
        EEE = (EEE, EEE)
        GGG = (GGG, GGG)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    @Test
    fun testParsing() {
        val subject = Day08(readInputData(inputData))

        assertEquals(7, subject.steps.size)
        assertEquals("AAA", subject.steps["AAA"]?.name)
        assertEquals("BBB", subject.steps["AAA"]?.left)

        assertEquals("RL", subject.instructions)
    }

    @Test
    fun testBasics() {
        val subject = Day08(readInputData(inputData))

        assertEquals("BBB", subject.steps["AAA"]?.left)
        assertEquals("CCC", subject.execute("AAA", 'R'))

        assertEquals(2, subject.getSolution1())
    }

    @Test
    fun testPuzzle1() {
        val subject = Day08(readInput(8, 2023))

        assertEquals(19199, subject.getSolution1())
    }

    val inputData2 = """
        LR

        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
    """.trimIndent()

    @Test
    fun testNavigation2() {
        val subject = Day08(readInputData(inputData2))
        val startSteps = subject.getStartSteps()
        assertEquals(arrayListOf("11A", "22A"), startSteps)
        assertEquals(listOf(2L, 3L), subject.getAllExecutionCounts())

        assertEquals(6, subject.getSolution2())
    }


    @Test
    fun testPuzzle2() {
        val subject = Day08(readInput(8, 2023))
        val startSteps = subject.getStartSteps()
        assertEquals(6, startSteps.size)
        assertEquals(13663968099527, subject.getSolution2())
    }
}