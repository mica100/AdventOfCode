package adventofcode.y2023

import adventofcode.readInput
import adventofcode.readInputData
import org.junit.Assert.*
import org.junit.Test

class Day02Test {

    @Test
    fun testCreateGameRound() {
        val gameRound = createGameRound(" 3 blue, 4 red")
        assertEquals(4, gameRound.red)
        assertEquals(3, gameRound.blue)
        assertEquals(0, gameRound.green)
    }

    @Test
    fun testValidGameRound() {
        val gameRound = GameRound(5, 6, 7)
        assertTrue(gameRound.isValid(9, 9, 9))
        assertTrue(gameRound.isValid(5, 6, 7))
        assertFalse(gameRound.isValid(4, 9, 9))
    }

    @Test
    fun testValidGames() {
        val game = Game(1, listOf(GameRound(5, 6, 7), GameRound(5, 4, 4)))
        assertTrue(game.isValid(9, 9, 9))
        assertTrue(game.isValid(5, 6, 7))
        assertFalse(game.isValid(4, 9, 9))
    }


    @Test
    fun testDay02basics() {
        val data = """
                Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
            """.trimIndent()

        val dataList = readInputData(data)
        val subject = Day02(dataList, 12, 14, 13)
        val gameList = subject.games
        println(gameList)
        assertEquals(5, gameList.size)
        assertEquals(1, gameList[0].id)

        val validIds = subject.checkValid()
        println("validIds $validIds")

        val minRBG = subject.getMinRedBlueGreen()

        minRBG.forEach {
            println("red ${it.red}, blue ${it.blue}, green ${it.green}")
        }
        println("sum2: ${subject.puzzle2Sum()}" )
    }

    @Test
    fun testDay02puzzle() {
        val inputList = readInput(2, 2023)
        val subject = Day02(inputList, 12, 14, 13)
        val sum = subject.puzzle1Sum()
        println("puzzle1: $sum")
    }

    @Test
    fun testDay02puzzle2() {
        val inputList = readInput(2, 2023)
        val subject = Day02(inputList, 12, 14, 13)
        val sum = subject.puzzle2Sum()
        println("puzzle2: $sum")
    }
}
