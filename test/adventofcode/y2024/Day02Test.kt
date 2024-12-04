package adventofcode.y2024

import adventofcode.readInput
import adventofcode.readInputData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day02Test: FunSpec() {
    private val testData = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent()

    init {
        test("check puzzle 1") {
            val day02 = Day02(readInputData(testData))
            day02.reports.size shouldBe 6
            day02.safeReportCount() shouldBe 2
            day02.safeReportsWithDampener() shouldBe 4
        }

        test("solve puzzle") {
            val day02 = Day02(readInput(2, 2024))
            day02.safeReportCount() shouldBe 549
            day02.safeReportsWithDampener() shouldBe 600
        }

        test("dropElement") {
            val data = listOf(0, 1, 2, 3)
            data.dropElement(0) shouldBe listOf(1,2,3)
            data.dropElement(1) shouldBe listOf(0,2,3)
            data.dropElement(3) shouldBe listOf(0,1,2)

        }
    }
}