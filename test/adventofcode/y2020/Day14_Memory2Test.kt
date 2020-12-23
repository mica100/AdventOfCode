package adventofcode.y2020

import org.junit.Test

import org.junit.Assert.*

class Day14Memory2Test {

    @Test
    fun setMem() {
    }

    @Test
    fun getVariant() {
        val m2 = Memory2()
        m2.setMask("X0XX")
        val l = m2.getVariant(2)
        println(l)
        assertEquals(8, l.size)
    }
}