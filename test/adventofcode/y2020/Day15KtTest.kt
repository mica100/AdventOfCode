package adventofcode.y2020

import org.junit.Assert.*
import org.junit.Test

class Day15KtTest {

    @Test
    fun testNextRound() {
        val game = MemoryGame(listOf(0, 3, 6))
        val result = listOf(0, 3, 6, 0, 3, 3, 1, 0 )

        for (input in 0..4)
            assertEquals(result[input], game.nextRound(input))
        // 4rd round: 6 for the first time in round 2
        // 5th round: for 0 came last time in round 0 -> 3 - 0 => 3
    }

    @Test
    fun testGame2020() {
        val game = MemoryGame(listOf(0, 3, 6))
        assertEquals(436, game.runGame(2020))

        val game2 = MemoryGame(listOf(2,3,1))
        assertEquals(78, game2.runGame(2020))
    }

}