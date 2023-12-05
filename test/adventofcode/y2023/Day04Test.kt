package adventofcode.y2023

import adventofcode.readInput
import adventofcode.readInputData
import org.junit.Assert.assertEquals
import org.junit.Test

class Day04Test {

    val inputData = """
        Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
        Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
        Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
        """.trimIndent()

    @Test
    fun testDay04Card() {
        val card = createCard(0, "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53")
        assertEquals("Card 1", card.name)
        assertEquals(listOf(41, 48, 83, 86, 17), card.winning)
        assertEquals(listOf(83, 86, 6), card.available.take(3))
        //assertEquals(8, subject.getPoints("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"))
        assertEquals(listOf(83, 86, 17, 48), card.getWinningNumbers())
        assertEquals(8, card.getWinningPoints())
    }

    @Test
    fun testDay04basics() {
        val subject = Day04(readInputData(inputData))
        assertEquals("Card 2", subject.cardList[1].name)
        assertEquals(arrayListOf(8, 2, 2, 1, 0, 0), subject.getWinningPoints())
        assertEquals(13, subject.getPuzzle1Sum())
    }

    @Test
    fun testDay04puzzle1() {
        val subject = Day04(readInput(4, 2023))
        assertEquals(25004, subject.getPuzzle1Sum())
    }

    @Test
    fun testDay04extended() {
        val subject = Day04(readInputData(inputData))

        assertEquals(2, subject.cardList[1].getWinningNumbers().size)

        subject.getWinningCopies(0)
        println(subject.cardList.map { "${it.idx} ${it.copyCount}"})

        assertEquals(listOf("2: Card 3", "3: Card 4"), subject.getWinningCopies(1).map { "${it.idx}: ${it.name}" })
        assertEquals(2, subject.cardList[1].copyCount)
        println(subject.cardList.map { "${it.idx} ${it.copyCount}"})

        assertEquals(2, subject.getWinningCopies(2).size)
        assertEquals(4, subject.cardList[2].copyCount)
        println(subject.cardList.map { "${it.idx} ${it.copyCount}"})

        assertEquals(1, subject.getWinningCopies(3).size)
        assertEquals(8, subject.cardList[3].copyCount)
        println(subject.cardList.map { "${it.idx} ${it.copyCount}"})

        assertEquals(0, subject.getWinningCopies(4).size)
        assertEquals(14, subject.cardList[4].copyCount)
        println(subject.cardList.map { "${it.idx} ${it.copyCount}"})

        assertEquals(listOf(1, 2, 4, 8, 14, 1), subject.cardList.map { it.copyCount })
    }

    @Test
    fun testDay04puzzle2() {
        val subject = Day04(readInput(4, 2023))
        assertEquals(14427616, subject.getPuzzle2NumberOfCards())
    }

}