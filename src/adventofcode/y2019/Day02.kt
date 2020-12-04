package adventofcode.y2019

import kotlin.collections.List as List1

/**
 * https://adventofcode.com/2019/day/2
 */

val InputData = "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,9,1,19,1,19,5,23,1,9,23,27,2,27,6,31,1,5,31,35,2,9,35,39,2,6,39,43,2,43,13,47,2,13,47,51,1,10,51,55,1,9,55,59,1,6,59,63,2,63,9,67,1,67,6,71,1,71,13,75,1,6,75,79,1,9,79,83,2,9,83,87,1,87,6,91,1,91,13,95,2,6,95,99,1,10,99,103,2,103,9,107,1,6,107,111,1,10,111,115,2,6,115,119,1,5,119,123,1,123,13,127,1,127,5,131,1,6,131,135,2,135,13,139,1,139,2,143,1,143,10,0,99,2,0,14,0"

fun main() {
    val opcodeList = mutableListOf<Int>()
    val inputStr = InputData
    // Part 0
    //val inputStr = readLine() ?: "0"
    //if (inputStr == "") return

    // Part 1
    opcodeList.clear()
    for (s in inputStr.split(",")) opcodeList.add(s.toInt())
    opcodeList[1] = 12
    opcodeList[2] = 2
    val output = processProgram(opcodeList)
    println("Part1 result " + output[0])

    // Part 2
    opcodeList.clear()
    for (s in inputStr.split(",")) opcodeList.add(s.toInt())

    for(i in 0..99) for(j in 0..99) {
        val ocList = opcodeList.toMutableList()
        ocList[1] = i
        ocList[2] = j
        //print("calc with op1=$i, op2=$j")
        val result = processProgram(ocList)[0]
        if (result == 19690720) {
            println("Part1 result result $result found with op1=$i, op2=$j")
            return
        }
    }
}

fun processProgram(opcodeList: MutableList<Int>) : List1<Int> {

    var finished = false
    var index = 0
    while (!finished && index < opcodeList.count()) {
        val id = opcodeList[index]
        when(id) {
            99 -> finished = true
            1 -> {
                val addressA = opcodeList[index + 1]
                val addressB = opcodeList[index + 2]
                val addressR = opcodeList[index + 3]
                opcodeList[addressR] = opcodeList[addressA] + opcodeList[addressB]
                index += 4
            }
            2 -> {
                val addressA = opcodeList[index + 1]
                val addressB = opcodeList[index + 2]
                val addressR = opcodeList[index + 3]
                opcodeList[addressR] = opcodeList[addressA] * opcodeList[addressB]
                index += 4
            }
            else -> {
                error("Error with opcode $id at index $index")
            }
        }
    }
    return opcodeList
}
