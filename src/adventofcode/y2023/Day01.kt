package adventofcode.y2023

class Day01(val dataInput: List<String>) {

    private fun parseData(data: List<String>): List<Int> {
        val numberList = data.map { line ->
            line.filter { c -> c.isDigit() }
        }.map { numberString -> numberString[0].digitToInt() * 10 + numberString[numberString.lastIndex].digitToInt() }
        return numberList
    }

    fun getData() = parseData(dataInput)

    fun getSumOfList() = getData().sum()

    private fun String.replaceDigits(): String {
        val numberNames = listOf( "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" )

        var newString = this
        numberNames.forEachIndexed { idx, name -> newString = newString.replace(name, name[0] + (idx+1).toString() + name.drop(1)) }
        return newString
    }

    fun getDataReplacedDigits(data: List<String>): List<String> =
        data.map{ it.replaceDigits() }

    fun getData2() = parseData(getDataReplacedDigits(dataInput))

    fun getSumOfList2() = parseData(getDataReplacedDigits(dataInput)).sum()
}
