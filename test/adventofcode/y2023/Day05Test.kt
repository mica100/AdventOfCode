package adventofcode.y2023

import adventofcode.readInput
import org.junit.Assert.assertEquals
import org.junit.Test

class Day05Test {

    val testInput = """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent()

    @Test
    fun testBasics() {
        val subject = Day05(testInput.reader().readLines())

        println(subject.calcMaps)
        assertEquals(7, subject.calcMaps.size)
        assertEquals(true, subject.seeds.contains(79))

        val calcMaps = subject.calcMaps
        println(calcMaps.listTo)
        assertEquals(7, calcMaps.listTo.size)
        println(calcMaps.getConversionSteps())
        assertEquals("soil-to-fertilizer", calcMaps.getConversionSteps()[1])

        val steps = calcMaps.getConversionSteps()
        val stepsAsList = calcMaps.stepsAsMapList(steps)

        assertEquals(82L, subject.calcMaps.convert(79, stepsAsList))
        assertEquals(43L, subject.calcMaps.convert(14, stepsAsList))
        assertEquals(86L, subject.calcMaps.convert(55, stepsAsList))
        assertEquals(35L, subject.calcMaps.convert(13, stepsAsList))

        assertEquals(Pair(13L, 35L), subject.calcMaps.getMinOf(subject.seeds))

        assertEquals(Pair(82L, 46L), subject.calcMaps.getMinOfSeedRanges(subject.seeds))
    }

    @Test
    fun testConversionMap() {
        val conversion: ConversionMap = createConversionMap("50 98 2")
        assertEquals(false, conversion.sourceRange.contains(97))
        assertEquals(true, conversion.sourceRange.contains(98))
        assertEquals(true, conversion.sourceRange.contains(99))
        assertEquals(false, conversion.sourceRange.contains(100))

        assertEquals(50L, conversion.convert(98))
        assertEquals(null, conversion.convert(49))
    }

    @Test
    fun testCalcMap() {
        val calc = CalcMap("from-to-target", "from", "target")
        calc.addConversion("50 98 2")
        calc.addConversion("52 50 48")

        assertEquals(55L, calc.convert(53))
        assertEquals(99L, calc.convert(97))
        assertEquals(50L, calc.convert(98))
        assertEquals(10L, calc.convert(10))
    }

    @Test
    fun testSolution1() {
        val subject = Day05(readInput(5, 2023))
        assertEquals(Pair(1921754120L, 825516882L), subject.calcMaps.getMinOf(subject.seeds))

    }

    @Test
    fun testSolution2() {
        val subject = Day05(readInput(5, 2023))
        assertEquals(Pair(3881100901L, 136096660L), subject.calcMaps.getMinOfSeedRanges(subject.seeds))
        // runs 14min08sec, parallelized 3min54sec
    }
}