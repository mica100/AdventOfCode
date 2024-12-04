package adventofcode.y2024

enum class Direction {
    forward,
    back,
    down,
    up,
    diag1,
    diag2,
    diag3,
    diag4,
}

class Day04(inputData: List<String>) {
    val charArray: List<List<Char>> = inputData.map { it.toCharArray().toList() }
    private val lineLen = charArray.firstOrNull()?.size ?: 0

    fun findX(char: Char, lineY: Int): List<Pair<Int, Int>> =
        charArray[lineY].mapIndexedNotNull { index, c -> if (c == char) Pair(index, lineY) else null }

    fun countAll() = charArray.foldIndexed(0) { index, count, _ ->
        count + findX('X', index).sumOf {
            checkAllXmas(
                it.first,
                it.second
            )
        }
    }

    fun checkAllXmas(startX: Int, startY: Int): Int = getWords(startX, startY).count { it == "XMAS" }

    fun getWords(startX: Int, startY: Int): List<String> {
        val wordEnd = 4 - 1
        val words = mutableListOf<String>()
        val wordHorizontal = StringBuilder()
        val wordHorizontalBack = StringBuilder()
        val wordVertikalDown = StringBuilder()
        val wordVertikalUp = StringBuilder()
        val wordDiag = List(4) { StringBuilder() }
        IntRange(0, wordEnd).forEach { offset ->
            getChar(startX + offset, startY)?.let { wordHorizontal.append(it) }
            getChar(startX - offset, startY)?.let { wordHorizontalBack.append(it) }
            getChar(startX, startY + offset)?.let { wordVertikalDown.append(it) }
            getChar(startX, startY - offset)?.let { wordVertikalUp.append(it) }

            getChar(startX + offset, startY - offset)?.let { wordDiag[0].append(it) }
            getChar(startX + offset, startY + offset)?.let { wordDiag[1].append(it) }
            getChar(startX - offset, startY + offset)?.let { wordDiag[2].append(it) }
            getChar(startX - offset, startY - offset)?.let { wordDiag[3].append(it) }
        }
        words.add(wordHorizontal.toString())
        words.add(wordHorizontalBack.toString())
        words.add(wordVertikalDown.toString())
        words.add(wordVertikalUp.toString())
        wordDiag.forEach { words.add(it.toString()) }
        //println(words)
        return words
    }

    fun getChar(x: Int, y: Int): Char? = if (x in 0 until lineLen && y in charArray.indices) {
        charArray[y][x]
    } else {
        null
    }

    fun getXWords(startX: Int, startY: Int): List<String> {
        val words = mutableListOf<String>()
        val wordBuilder = List(Direction.values().size) { StringBuilder() }

        IntRange(-1, 1).forEach { offset ->
            getChar(startX + offset, startY + offset)?.let { wordBuilder[0].append(it) }
            getChar(startX + offset, startY - offset)?.let { wordBuilder[1].append(it) }
        }
        wordBuilder[0].toString().let { words.add(it) }
        wordBuilder[1].toString().let { words.add(it) }
        //println(words)
        return words
    }

    fun checkWords(words: List<String>, match: String): Boolean =
        (words.size == 2) &&
           (words[0] == match || words[0] == match.reversed()) &&
                    (words[1] == match || words[1] == match.reversed())


    fun countXWords() = charArray.foldIndexed(0) { index, count, _ ->
        count + findX('A', index).count {
            checkWords(getXWords(it.first, it.second), "MAS")
        }
    }
}
