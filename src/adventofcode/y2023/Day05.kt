package adventofcode.y2023

data class CalcMap(val name: String) {
    val calc = mutableListOf<String>()
}

class Day05(val inputList: List<String>) {

    val maps: MutableMap<String, List<String>> = mutableMapOf()

    init {
        var currentMap: CalcMap? = null

        inputList.forEach { line ->
            if (line.endsWith(" map:")) {
                val name = line.substringBeforeLast(" map:")
                maps.getOrPut(name, ::emptyList)
            } else if (line.startsWith("seeds: ")) {
                maps.getOrPut("seeds") { line.split(":").drop(1)}
            }
        }

    }


}
