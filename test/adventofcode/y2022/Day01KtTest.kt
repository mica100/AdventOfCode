package adventofcode.y2022

import adventofcode.readInput
import io.kotest.matchers.shouldBe
import org.junit.Test

import org.junit.Assert.*

class Day01KtTest {

    @Test
    fun testDay01basics() {
        val data = """
            1000
            2000
            3000
            
            4000
            
            5000
            6000
            
            7000
            8000
            9000
            
            10000
            """.trimIndent()

        val dataList = data.split("\r?\n|\r".toRegex())
        dataList.size shouldBe 14
        dataList[0] shouldBe "1000"
        dataList[1] shouldBe "2000"

        val day01 = Day01(dataList)
        assertEquals(day01.elfCaloriesList.size, 5)

        day01.elfCaloriesList[0] shouldBe 6000
        day01.elfCaloriesList[1] shouldBe 4000
        day01.elfCaloriesList[3] shouldBe 24000

        day01.getMaxCaloriesElf() shouldBe Pair(3, 24000)
        day01.getMaxCaloriesAtOneElf() shouldBe 24000
    }

    @Test
    fun testPart1() {
        val day01 = Day01(readInput(1,2022))

        day01.getMaxCaloriesAtOneElf() shouldBe 74198
    }

    @Test
    fun testPart2() {
        val day01 = Day01(readInput(1,2022))

        day01.getTop3CaloriesInSum() shouldBe 209914
    }
}