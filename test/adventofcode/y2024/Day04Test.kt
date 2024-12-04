package adventofcode.y2024

import adventofcode.readInput
import adventofcode.readInputData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day04Test : FunSpec() {

    val testData = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent()

    init {
        test("check example") {
            val day04 = Day04(readInputData(testData))
            day04.charArray.size shouldBe 10
            day04.charArray[0] == "MMMSXXMASM".toCharArray().toList()
            day04.findX('X', 0) shouldBe listOf(4 to 0, 5 to 0)
            day04.getWords(5, 0).size shouldBe 8
            day04.checkAllXmas(5, 0) shouldBe 1
            day04.countAll() shouldBe 18
        }

        test("puzzle 1") {
            val day04 = Day04(readInput(4, 2024))
            day04.countAll() shouldBe 2532
        }

        test("example 2") {
            val testData = """
                .M.S......
                ..A..MSMS.
                .M.S.MAA..
                ..A.ASMSM.
                .M.S.M....
                ..........
                S.S.S.S.S.
                .A.A.A.A..
                M.M.M.M.M.
                ..........
            """.trimIndent()

            val day04 = Day04(readInputData(testData))
            day04.getXWords(2, 1).size shouldBe 2
            day04.countXWords() shouldBe 9
        }

        test("puzzle 2") {
            val day04 = Day04(readInput(4, 2024))
            day04.countXWords() shouldBe 1941
        }
    }

}