package adventofcode.y2023

import adventofcode.readInput
import adventofcode.readInputData
import org.junit.Assert.assertEquals
import org.junit.Test

class Day07Test {

    val inputData = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()

    @Test
    fun testParsing() {
        val subject = Day07(readInputData(inputData))
        assertEquals(5, subject.hands.size)

        assertEquals(2, subject.hands[0].getType())
        assertEquals(4, subject.hands[1].getType())
        assertEquals(3, subject.hands[2].getType())
        assertEquals(3, subject.hands[3].getType())

        assertEquals(1, subject.hands[2].compareTo(subject.hands[3]))
        assertEquals(-1, subject.hands[3].compareTo(subject.hands[2]))

//        assertEquals(listOf(4, 8, 9), subject.races.map { it.getValidDistance() })
//
        assertEquals(6440, subject.getSolution1())
    }

    @Test
    fun testHand() {
        val hand = Hand("KK677", 0)
        assertEquals(13, hand.getCardValue('A'))
        assertEquals(12, hand.getCardValue('K'))
        assertEquals(1, hand.getCardValue('2'))
    }

    @Test
    fun testSolution1() {
        val subject = Day07(readInput(7, 2023))
        assertEquals(250058342, subject.getSolution1())
    }

    @Test
    fun testJokers() {
        val subject = Day07(readInputData(inputData).map { it.replace('J', '*') })
        assertEquals(5905, subject.getSolution1())
    }

    @Test
    fun testSolution2() {
        val subject = Day07(readInput(7, 2023).map { it.replace('J', '*') })
        assertEquals(250506580, subject.getSolution1())
    }

}