package adventofcode.y2016

import adventofcode.generic.Point
import adventofcode.readInput
import adventofcode.y2016.FacingDirection.*
import adventofcode.y2016.TurningDirection.*
import io.kotest.matchers.shouldBe
import org.junit.Test

class Day01Test {

    @Test
    fun part1() {
        val data = "R2, L3"
        val day01 = Day01()
        day01.part1(listOf(Instruction(R,2), Instruction(L, 3))) shouldBe 5
    }

    @Test
    fun test_parse() {
        val data = "R2, L3"
        Instruction.listFromString(data) shouldBe listOf(Instruction(R,2), Instruction(L, 3))
    }

    @Test
    fun test_turn() {
        val instruction = Instruction(R, 0)

        instruction.turn(N) shouldBe E
        instruction.turn(E) shouldBe S
        instruction.turn(S) shouldBe W
        instruction.turn(W) shouldBe N
    }

    @Test
    fun test_turn_left() {
        val instruction = Instruction(L, 0)

        instruction.turn(E) shouldBe N
        instruction.turn(S) shouldBe E
        instruction.turn(W) shouldBe S
        instruction.turn(N) shouldBe W
    }

    @Test
    fun test_move() {
        Instruction(R, 1).move(N, Point(0, 0)) shouldBe Pair(E, Point(1, 0))

        val data = "R2, L3"
        val day01 = Day01()
        day01.part1(Instruction.listFromString(data)) shouldBe 5
    }

    @Test
    fun test_whole_solution_part1() {
        val data = readInput(1, 2016)[0]
        val day01 = Day01()
        day01.part1(Instruction.listFromString(data)) shouldBe 301
    }

    @Test
    fun test_part2() {
        val data = "R8, R4, R4, R8"
        val day01 = Day01()
        day01.part2(Instruction.listFromString(data)) shouldBe 4
    }

    @Test
    fun test_whole_solution_part2() {
        val data = readInput(1, 2016)[0]
        val day01 = Day01()
        day01.part2(Instruction.listFromString(data)) shouldBe 169
    }

}