package adventofcode

fun main() {
    var floor = 0
    var index = 1
    var storeIndex = -1

    val inputStr = readInput(1)[0]

    for (c in inputStr) {
        when(c) {
            '(' -> floor++
            ')' -> floor--
        }
        if(floor < 0 && storeIndex == -1) {
            storeIndex = index
        }
        index++
    }

    print("floor=${floor}, storeIndex=$storeIndex")
}