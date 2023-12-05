package adventofcode.y2023

import kotlin.math.pow

data class Card(val idx: Int, val name: String, val winning: List<Int>, val available: List<Int>) {
    var copyCount: Int = 1
    fun getWinningNumbers(): List<Int> = available.filter { winning.contains(it) }
    fun getWinningPoints(): Int = 2.0.pow(getWinningNumbers().size - 1).toInt()
}

fun createCard(idx: Int, inputLine: String): Card {
    val (cardName, winningStr, myNumbers) = inputLine.split(Regex("[:|]"))
    return Card(idx, cardName.trim(), winningStr.toIntList(), myNumbers.toIntList())
}

fun String.toIntList(): List<Int> = trim().split(Regex(" +")).map { it.toInt() }

class Day04(inputData: List<String>) {
    val cardList = inputData.mapIndexed { idx, it -> createCard(idx, it) }

    fun getWinningPoints(): List<Int> = cardList.map { it.getWinningPoints() }
    fun getPuzzle1Sum(): Int = getWinningPoints().sum()

    fun getPuzzle2NumberOfCards(): Int {
        cardList.indices.forEach { idx ->
            getWinningCopies(idx)
        }
        return cardList.sumOf { it.copyCount }
    }

    fun getWinningCopies(cardIdx: Int): List<Card> {
        val card = cardList[cardIdx]
        val winningCount = card.getWinningNumbers().size
        return if (winningCount > 0) {
            val copyCount = card.copyCount

            val start = cardIdx + 1
            val end = minOf(start + winningCount, cardList.size)
            cardList.subList(start, end)
                .also { subListCard -> subListCard.map { card -> card.copyCount += copyCount } }
        } else {
            emptyList()
        }
    }
}
