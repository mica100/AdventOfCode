package adventofcode.y2023

import adventofcode.readInput
import adventofcode.readInputData
import org.junit.Assert.*
import org.junit.Test

class Day01Test {

    @Test
    fun testDay01basics() {
        val data = """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet
            """.trimIndent()

        val dataList = readInputData(data)
        val subject = Day01(dataList)
        val numberList = subject.getData()
        println(numberList)
        assertTrue(dataList.size == 4)

        val sumOfValues = subject.getSumOfList()
        println(sumOfValues)
        assertEquals(142, sumOfValues)
    }

    @Test
    fun testDay01puzzel1() {
        val data = readInput(1, 2023)
        val subject = Day01(data)
        val sum = subject.getSumOfList()
        println("puzzle1: $sum")
    }

    @Test
    fun testDay01parseDigitNames() {
        val data = """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
        """.trimIndent()

        val dataList = readInputData(data)
        val subject = Day01(dataList)
        val replacedList = subject.getDataReplacedDigits(dataList)
        println("replacedList: $replacedList")
        val numberList = subject.getData2()
        println("numberList: $numberList")
        val sum2 = subject.getSumOfList2()
        println("sum2: $sum2")
    }

    @Test
    fun testDay01puzzel2() {
        val data = readInput(1, 2023)
        val subject = Day01(data)
        val sum = subject.getSumOfList2()
        println("puzzle2: $sum")
    }
}
