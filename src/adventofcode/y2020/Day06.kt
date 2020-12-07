package adventofcode.y2020

import adventofcode.readInput

class Group () {
    var answerList = mutableListOf<String>()

    companion object {
        val groupList = mutableListOf<Group>()
        var currentGroup = Group()
    }

    fun answer(answer: String) {
        answerList.add(answer)
    }

    fun count(): Int {
        var uniqAnswers = ""
        for (answer in answerList) {
            for (ch in answer) {
                if (! uniqAnswers.contains(ch)) uniqAnswers += ch
            }
        }
        return uniqAnswers.length
    }

    fun count2(): Int {
        var uniqAnswers = ""
        for (answer in answerList) {
            if (uniqAnswers == "") {
                uniqAnswers = answer
            } else {
                for (ch in uniqAnswers) {
                    if (! answer.contains(ch)) uniqAnswers = uniqAnswers.filter { it != ch }
                }
                if (uniqAnswers.isEmpty()) return 0
            }
        }
        return uniqAnswers.length
    }

}

fun readGroupInput(inputList: List<String>): MutableList<Group> {

    var currentGroup = Group()
    for (input in inputList) {
        if (input.isBlank() or input.isEmpty()) {
            Group.groupList.add(currentGroup)
            currentGroup = Group()
        }

        currentGroup.answer(input)
    }
    Group.groupList.add(currentGroup)
    return Group.groupList
}

fun main() {
    val inputStrList = readInput(6, 2020)

    val groups = readGroupInput(inputStrList)

    val allAnwers = groups.map { it.count() }.sum()
    val allAnwers2 = groups.map { it.count2() }.sum()
    println("groups: ${groups.size}, answers: ${allAnwers}, answers: ${allAnwers2}")
}