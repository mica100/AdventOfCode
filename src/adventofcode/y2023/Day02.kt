package adventofcode.y2023

data class GameRound(val red: Int, val blue: Int, val green: Int)

fun GameRound.isValid(maxRed: Int, maxBlue: Int, maxGreen: Int) =
    red <= maxRed && blue <= maxBlue && green <= maxGreen

data class Game(val id: Int, val gameRoundList: List<GameRound>)

fun Game.isValid(maxRed: Int, maxBlue: Int, maxGreen: Int): Boolean =
    gameRoundList.all { it.isValid(maxRed, maxBlue, maxGreen) }

fun Game.getMin(): GameRound =
    gameRoundList.fold(GameRound(0,0,0)) { acc, gameRound ->
        GameRound(
            maxOf(acc.red, gameRound.red),
            maxOf(acc.blue, gameRound.blue),
            maxOf(acc.green, gameRound.green),
        )
    }

fun createGameRound(gameRoundStr: String): GameRound {
    var red = 0
    var blue = 0
    var green = 0
    gameRoundStr.split(',').forEach {
        val (count, color) = it.trim().split(" ")
        //println("gameRound $gameRoundStr -> it $it, count $count, color $color")
        when(color) {
            "red" -> red = count.toInt()
            "blue" -> blue = count.toInt()
            "green" -> green = count.toInt()
        }
    }
    return GameRound(red, blue, green)
}


class Day02(
    val data: List<String>,
    val maxRed: Int, val maxBlue: Int, val maxGreen: Int
) {

    val games = parseData()

    fun parseData(): List<Game> {
        val gameList = data.map { line ->
            val (gameName, gameRounds) = line.split(":", limit = 2)
            val gameId = gameName.replace("Game ", "").toInt()
            val roundList = gameRounds.split(';')
            val gameRoundList = roundList.map { createGameRound(it) }

            Game(gameId, gameRoundList)
        }
        return gameList
    }

    fun checkValid(): List<Int> {
        return games.filter { it.isValid(maxRed, maxBlue, maxGreen) }.map { it.id }
    }

    fun puzzle1Sum() = checkValid().sum()

    fun getMinRedBlueGreen (): List<GameRound> {
        return games.map { game -> game.getMin() }
    }

    fun puzzle2Sum() = getMinRedBlueGreen().sumOf { it.red * it.blue * it.green }

 }