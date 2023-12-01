package adventofcode.y2022

class Day01(data: List<String>) {

    val elfCaloriesList = parseData(data)

    private fun parseData(data: List<String>): List<Int> {
        val elfFoldData = data.fold(Pair(0, mutableListOf<Int>())) { (currentElfId, elfList), line ->
            val calories = line.toIntOrNull()
            val newElfId = if (calories != null) {
                if (elfList.size <= currentElfId) {
                    elfList.add(0)
                }
                elfList[currentElfId] += calories
                currentElfId
            } else {
                currentElfId+1
            }
            Pair(newElfId, elfList)
        }

        return elfFoldData.second
    }

    fun getMaxCaloriesAtOneElf(): Int {
        return elfCaloriesList.maxOf { it }
    }

    fun getMaxCaloriesElf(): Pair<Int, Int> {
        return elfCaloriesList.mapIndexed { i, calories -> Pair(i, calories) }.sortedBy { it.second }.last()
    }

    fun getTop3CaloriesInSum(): Int {
        return elfCaloriesList.sorted().takeLast(3).sum()
    }
}