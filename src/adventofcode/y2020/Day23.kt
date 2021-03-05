package adventofcode.y2020

import adventofcode.readInput

fun main() {
    val cupsInput = readInput(23, 2020)[0]

    val cups = Cups(cupsInput.fold(mutableListOf<Int>()) { acc, c -> acc += "$c".toInt(); acc })

    println("cups are $cups")
    cups.play(100)
    println(cups.getResult())

    cups.fillup(1000000)
    cups.play(10000000)
    println(cups.getResult2())
}

class Cups(var cups: MutableList<Int>) {
    var currentIdx = 0
    val maxLabel = cups.max()!!

    fun play(rounds: Int) {
        for (i in 1..rounds) {
            playRound()
            if (rounds < 120)
                println("$i: ${getOutput()}")
            else if (i < 100 || i % 1000 == 0)
                println("$i: ${cups.indexOf(1)}")
        }
    }

    fun fillup(limit: Int) {
        cups.addAll((maxLabel+1) until limit)
    }

    fun playRound() {
        val pop3 = pop3()
        val idx = findNext()

        if (idx != null) {
            cups.addAll(idx+1, pop3)
            val firstElement = cups.removeAt(0)
            cups.add(firstElement)
        } else {
            throw IndexOutOfBoundsException("Can't find insert position")
        }
    }

    fun findNext(): Int? {
        var findLabel = if (cups[0] > 1) cups[0] - 1 else maxLabel
        while (! cups.contains(findLabel)) {
            findLabel--
            if (findLabel < 0) findLabel = maxLabel
        }

        for (idx in cups.indices) {
            if (cups[idx] == findLabel) {
                return idx
            }
        }
        return null
    }

    fun pop3(): List<Int> {
        val pop3 = cups.subList(1, 4)
        cups = cups.toList().filterIndexed { index, i -> !(index in 1..3) }.toMutableList()
        return pop3
    }

    fun getResult(): String {
        val label1Idx = cups.indexOf(1)

        val resultList = listOf(cups[label1Idx]) + cups.subList(label1Idx+1, cups.size) + cups.subList(0, label1Idx)

        println(getOutput(resultList))

        return resultList.drop(1).fold("") { acc, i -> acc+"$i" }
    }

    fun getResult2(): Long {
        val label1Idx = cups.indexOf(1)
        return cups[label1Idx+1].toLong() * cups[label1Idx+2]
    }

    fun getOutput(list: List<Int> = cups): String {
        return cups.foldIndexed("") { idx, acc, i -> if (idx == currentIdx) "$acc ($i)" else "$acc $i" }
    }

    override fun toString(): String {
        return super.toString() + ", cups:" + getOutput()
    }
}