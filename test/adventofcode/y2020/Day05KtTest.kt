package adventofcode.y2020

import org.junit.Test

import org.junit.Assert.*

class Day05KtTest {

    @Test
    fun testGetRow() {
        assertEquals(0, getRow("FFF"))
        assertEquals(1, getRow("FFB"))
        assertEquals(2, getRow("FFBF"))
        assertEquals(44, getRow("FBFBBFF"))
    }

    @Test
    fun testGetCol() {
        assertEquals(3, getCol("LRR"))
    }

    @Test
    fun testGetSeat() {
        assertEquals(Pair(5, 44), getSeat("FBFBBFFRLR"))
        assertEquals(357, getSeatID("FBFBBFFRLR"))

        assertEquals(Pair(7, 70), getSeat("BFFFBBFRRR"))
        assertEquals(567, getSeatID("BFFFBBFRRR"))

    }
}