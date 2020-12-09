package adventofcode.y2020

import adventofcode.readInput

class Buffer(private val data: LongArray, private val checkLength: Int) {
    fun check(idx: Int): Boolean {
        val sum = data[idx]
        for (i in idx-checkLength until idx) {
            for (j in i+1 until idx) {
                if (idx == 90) {
                    //println("$idx ${data[i]}($i) + ${data[j]}($j) == ${data[i] + data[j]} != $sum")
                }
                if (data[i] != data[j] && data[i] + data[j] == sum) {
                    //println("$idx ${data[i]}($i) + ${data[j]}($j) == ${data[i] + data[j]} == $sum")
                    return true
                }
            }
        }
        return false
    }

    fun find(targetSum: Long): Pair<Long?, Long?> {
        for (i in data.indices) {
            var j = i+1
            var sum = data[i]
            while (sum < targetSum) {
                sum+=data[j++]
            }
            if (sum == targetSum) {
                val min = data.slice(i..j).min()
                val max = data.slice(i..j).max()
                return Pair(min, max)
            }
        }
        return Pair(null, null)
    }
}

fun main() {
    val data = readInput(9, 2020).map { it.toLong() }.toLongArray()

    val startIdx = 25
    val buffer = Buffer(data, startIdx)

    var failedOn = 0L
    for (idx in startIdx until data.size) {
        if (!buffer.check(idx)) {
            println("$idx ${data[idx]} failed")
            failedOn = data[idx]
            break
        } else {
            //println("$idx ${data[idx]} ok")
        }
    }

    // find a continuous region that sum up to this "failedOn"
    val (min, max) = buffer.find(failedOn)
    if (min!=null && max!=null) {
        println("found sum $failedOn in range [${min}, ${max}] = ${min+max}")
    }
}