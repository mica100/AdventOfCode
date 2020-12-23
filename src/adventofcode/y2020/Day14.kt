package adventofcode.y2020

import adventofcode.readInput

fun main() {
    val instructionList = readInput(14, 2020).map { createInstruction(it) }

    val mem = Memory()

    instructionList.forEach { it?.execute(mem) }
    println(mem.mem)
    println("memory sum ${mem.sum()}")

    val mem2 = Memory2()
    instructionList.forEach { it?.execute(mem2) }
    println(mem2.mem)
    println("memory sum ${mem2.sum()}")


}

abstract class InstructionAbstract() {
    abstract fun execute(mem: Memory)
}

class InstSetMask(val mask: String): InstructionAbstract() {
    override fun execute(mem: Memory) {
        mem.setMask(mask)
    }
}

class InstSetMem(val idx: Long, val value: Long): InstructionAbstract() {
    override fun execute(mem: Memory) {
        mem.setMem(idx, value)
    }
}

fun createInstruction(input: String): InstructionAbstract? {
    val (cmd, value) = input.split("=").map { it.trim() }
    val regex = """[0-9]+""".toRegex()

    if (cmd == "mask") {
        return InstSetMask(value)
    }
    if (cmd.startsWith("mem[")) {
        val match = regex.find(cmd)
        if (match != null)
        {
            return InstSetMem(match.value.toLong(), value.toLong())
        }
    }
    return null
}

open class Memory() {
    val mem = mutableMapOf<Long, Long>()
    var mask0 = 0L.inv() // 0xFFFFFFFFFL
    open var mask1 = 0x000000000L

    open fun setMem(idx: Long, value: Long) {
        val newValue = (value or mask1) and mask0   // apply 1 and 0 to newValue
        mem[idx] = newValue
    }
    fun getMem(idx: Long): Long {
        return mem.getOrDefault(idx, 0)
    }

    open fun setMask(input: String) {
        // example XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
        input.reversed().forEachIndexed() { idx, c -> when(c) {
            '0' -> {
                //println("mask and with ${(((1L shl idx).inv() and 0xFFFFFFFFFF)).toString(2)}")
                mask0 = mask0 and ((1L shl idx).inv() and 0xFFFFFFFFFF)
                mask1 = mask1 and ((1L shl idx).inv() and 0xFFFFFFFFFF)
            }
            '1' -> {
                //println("mask or with ${(1L shl idx).toString(2)}")
                mask0 = mask0 or (1L shl idx)
                mask1 = mask1 or (1L shl idx)
            }
            'X' -> {
                //println("mask reset at idx $idx")
                mask0 = mask0 or (1L shl idx)
                mask1 = mask1 and (1L shl idx).inv()
            }
        } }
    }

    fun sum(): Long {
        return mem.values.sum()
    }
}

class Memory2(): Memory() {
    var maskStr: String = ""

    override fun setMask(input: String) {
        maskStr = input
        super.setMask(input)
    }

    override fun setMem(idx: Long, value: Long) {
        val newIdx = (idx or super.mask1) // and mask0   // apply 1 and 0 to newValue

        for (idxVariant in getVariant(newIdx)) mem[idxVariant] = value
    }

    fun getVariant(address: Long): List<Long> {
        val variants = mutableSetOf<Long>(address)
        maskStr.reversed().forEachIndexed { index, c ->
            if (c == 'X') {
                val roSet = variants.toSet()
                roSet.forEach { it ->
                    val addr1 = it or (1L shl index)
                    variants.add(addr1)
                    val addr0 = it and (1L shl index).inv() and 0xFFFFFFFFFF
                    variants.add(addr0)
                }
            }
        }
        return variants.toList()
    }
}