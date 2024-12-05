package adventofcode.y2024

import adventofcode.readInput
import adventofcode.readInputData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day05Test : FunSpec() {

    val testData = """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13
        
        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
    """.trimIndent()

    init {
        test("check example") {
            val d = Day05(readInputData(testData))
            d.rules.size shouldBe 21
            d.rules[20] shouldBe Rule(53, 13)
            d.updates.size shouldBe 6
            d.getRules(listOf(75)).size shouldBe 6
            for (updateNr in 0.. 2) {
                d.isOrdered(d.updates[updateNr]) shouldBe true
            }
            for (updateNr in 3.. d.updates.size-1) {
                d.isOrdered(d.updates[updateNr]) shouldBe false
            }
            d.middlePage(d.updates[0]) shouldBe 61
            d.sumMiddlePages() shouldBe 143
        }

        test("puzzle 1") {
            val d = Day05(readInput(5, 2024))
            d.sumMiddlePages() shouldBe 5064
        }

        test("example 2") {
            val d = Day05(readInputData(testData))
            //d.sumMiddlePagesFromIncorrect() shouldBe 123
        }

        test("puzzle 2") {
            val d = Day05(readInput(5, 2024))
            //d.sumMiddlePagesFromIncorrect() shouldBe 100
        }
    }

}