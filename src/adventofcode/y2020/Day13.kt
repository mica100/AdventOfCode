package adventofcode.y2020

import adventofcode.readInput
import adventofcode.generic.Point

fun main() {
    val lines = readInput(13, 2020)

    val sch = Schedule()

    sch.readBusIds(lines[1])
    sch.findNextDep(lines[0].toInt())

    val s2 = Schedule2()

    s2.readBusIds(lines[1])
    s2.findDeptTime()

}

class Schedule() {
    val busList = mutableListOf<Int>()

    fun readBusIds(idLine: String) {
        busList.addAll(idLine.split(",").filter { it != "x" }.map { it.toInt() })
    }

    fun findNextDep(startTime: Int) {
        val deptMap = mutableMapOf<Int, Int>()

        for (bus in busList) {
            val modulo = startTime % bus // 15 % 7 -> 1 d.h. in 7-1 = 6min Abfahrt
            val wait = bus - modulo
            deptMap[bus] = wait
        }

        val minBus = deptMap.minByOrNull { it -> it.value }

        println("bus ${minBus?.key} arrives after ${minBus?.value}min waiting time. ${minBus?.key!! *minBus?.value}")
    }
}

data class Bus(val id: Long, val offset: Int) {

    /* return true if baseTime+offset is a multiple of this bus (id) */
    fun checkTime(baseTime: Long): Boolean = ((baseTime+offset) % id) == 0L

    fun getBase(busTime: Long): Long =  busTime - offset
}

class Schedule2() {
    val busList = mutableListOf<Bus>()
    val matchMap = mutableMapOf<Int, Long>()
    val matchDist = mutableMapOf<Int, Long>()
    var newDelta = 1L

    fun readBusIds(idLine: String) {
        idLine.split(",").forEachIndexed { index, s ->
            if ( s != "x" ) {
                busList.add(Bus(s.toLong(), index))
            }
        }
    }

    inline fun checkTime(busses: List<Bus>, startTime: Long): Boolean {
        var idx = 0
        for (bus in busses) {
            if (! bus.checkTime(startTime)) {
                println(" failed at idx ${bus.id}")
                return false
            } else {
                print(" ok:${bus.id},")
                if (! matchMap.containsKey(idx+2)) {
                    matchMap[idx+2] = startTime
                } else if (! matchDist.containsKey(idx+2)) {
                    newDelta = startTime - matchMap[idx+2]!!
                    matchDist[idx+2] = newDelta
                }
            }
            idx++
        }
        return true
    }

    fun findDeptTime() {
        var startFactor = 1L
        val sortedBusses = busList.sortedBy { bus -> bus.id }.reversed().toTypedArray()
        val bus1 = sortedBusses[0]
        newDelta = bus1.id
        val otherBusses = sortedBusses.drop(1)
        var startTime = bus1.getBase(newDelta)
        while (true) {
            if (checkTime(otherBusses, startTime)) break

            startTime += newDelta
            startFactor++
            /* if (startFactor % 1000000 == 0L) */ print("$startFactor, $startTime, $newDelta")
        }
        println("matchMap: $matchMap")
        println("matchDist: $matchDist")
        println("result 2: startFactor=$startFactor, startTime=${startTime}")
    }
}