package adventofcode.y2024

import adventofcode.readInput
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day03Test : FunSpec() {

    init {
        test("check example") {
            val testData = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
            val day03 = Day03(testData)
            day03.operationList.size shouldBe 4
            day03.multiplyResult() shouldBe 161L
        }

        test("check example 2") {
            val testData = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
            val day03 = Day03(testData, enableConditions = true)
            println(day03.operationList)
            day03.multiplyResult() shouldBe 48L
        }

        test("puzzle 1") {
            val day03 = Day03(readInput(3, 2024).joinToString())
            day03.operationList.size shouldBe 650
            day03.multiplyResult() shouldBe 157621318L
        }

        test("puzzle 2") {
            val day03 = Day03(readInput(3, 2024).joinToString(), enableConditions = true)
            day03.operationList.size shouldBe 340
            day03.multiplyResult() shouldBe 79845780L
        }
    }

}