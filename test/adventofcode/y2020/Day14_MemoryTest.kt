package adventofcode.y2020

import org.junit.Test

import org.junit.Assert.*

class Day14MemoryTest {

    @Test
    fun setMem() {
        val mem = Memory()
        mem.setMask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")
        assertEquals(0, mem.getMem(0))
        mem.setMem(8, 11)
        assertEquals(0b01001001, mem.getMem(8))
        mem.setMem(7, 101)
        assertEquals(0b01100101, mem.getMem(7))
        mem.setMem(8, 0)
        assertEquals(0b01000000, mem.getMem(8))

        assertEquals(101 + 64, mem.sum())
    }

    @Test
    fun setMask() {
        val mem = Memory()
        mem.setMask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX0X")
        //println("${mem.mask0.toString(16)} ${mem.mask0.toString(2)}")
        //println("${mem.mask1.toString(16)} ${mem.mask1.toString(2)}")
        assertEquals(0xFFFF - 2, mem.mask0 and 0xFFFF) // compare last 4 Bytes only
        assertEquals(0, mem.mask1)

        mem.setMask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
        //println("${mem.mask0.toString(16)} ${mem.mask0.toString(2)}")
        //println("${mem.mask1.toString(16)} ${mem.mask1.toString(2)}")
        assertEquals(0xFFFF, mem.mask0 and 0xFFFF) // compare last 4 Bytes only
        assertEquals(0, mem.mask1)

        mem.setMask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XX")
        //println("mask0: ${mem.mask0.toString(16)} ${mem.mask0.toString(2)}")
        //println("mask1: ${mem.mask1.toString(16)} ${mem.mask1.toString(2)}")
        assertEquals(0xFFFF, mem.mask0 and 0xFFFF) // compare last 4 Bytes only
        assertEquals(0x4, mem.mask1)

        mem.setMask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")
        //println("mask0: ${mem.mask0.toString(16)} ${mem.mask0.toString(2)}")
        //println("mask1: ${mem.mask1.toString(16)} ${mem.mask1.toString(2)}")
        assertEquals(2L.inv() and 0xFFFF, mem.mask0 and 0xFFFF) // compare last 4 Bytes only
        assertEquals(64, mem.mask1)

    }

}