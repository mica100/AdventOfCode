package adventofcode.y2024

import adventofcode.readInput
import adventofcode.readInputData
import io.kotest.core.spec.style.FunSpec

class Day00Test : FunSpec() {

    val testData = """
    """.trimIndent()

    init {
        test("check example") {
            val day05 = Day05(readInputData(testData))
        }

        test("puzzle 1") {
            val day05 = Day05(readInput(5, 2024))
            //day05.countAll() shouldBe 2532
        }

        test("example 2") {
            val testData = """
            """.trimIndent()

            val day05 = Day05(readInputData(testData))
        }

        test("puzzle 2") {
            val day05 = Day05(readInput(5, 2024))
        }
    }

}