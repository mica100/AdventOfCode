package adventofcode.y2023

val allCards = "A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2, *".replace(", ", "")
    .map { it }.reversed().joinToString("")


data class Hand(val handStr: String, val bit: Int) : Comparable<Hand> {
    fun getType(): Int {
        val sortedHand = handStr.map { it }.sorted()
        val sortedMap = sortedHand.filterNot { it == '*' }.groupBy { it }
        val jokerCount = sortedHand.count { it == '*' }
        val groupedList = sortedMap.map { (k, v) -> Pair(k, v.size) }.sortedByDescending { it.second }
        val sizeList = groupedList.map { it.second }

        return when {
            sizeList.isEmpty() && jokerCount == 5 -> 7
            sizeList.first() + jokerCount == 5 -> 7
            sizeList.first() + jokerCount == 4 -> 6
            sizeList.first() + jokerCount == 3 && sizeList[1] == 2 -> 5 // full house
            sizeList.first() + jokerCount == 3 -> 4
            sizeList.isTwoPair() -> 3
            sizeList.first() + jokerCount == 2 -> 2
            else -> 1
        }
    }

    override fun compareTo(other: Hand): Int {
        val ourType = getType()
        val theirType = other.getType()
        if (ourType > theirType)
            return 1
        else if (ourType < theirType)
            return -1

        for (i in 0..handStr.length) {
            val our = getCardValue(handStr[i])
            val their = getCardValue(other.handStr[i])
            when {
                (our > their) -> return 1
                (our < their) -> return -1
            }
        }
        return 0
    }

    fun getCardValue(card: Char) = allCards.indexOf(card)
    //.also { println("value card $card -> $it") }
}


private fun List<Int>.isFive() = first() == 5
private fun List<Int>.isFour() = first() == 4
private fun List<Int>.isFullHouse() = this[0] == 3 && this[1] == 2
private fun List<Int>.isThree() = this[0] == 3
private fun List<Int>.isTwoPair() = this[0] == 2 && this[1] == 2
private fun List<Int>.isPair() = this[0] == 2

fun String.parseHand() = split(" ").let { Hand(it[0], it[1].toInt()) }

class Day07(inputData: List<String>) {

    val hands = inputData.map { it.parseHand() }

    fun getSortedHands() = hands.sorted()

    fun getSolution1() = getSortedHands().mapIndexed { idx, hand ->
        idx.plus(1) * hand.bit
    }.sum()
}
