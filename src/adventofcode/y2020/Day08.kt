package adventofcode.y2020

import adventofcode.readInput

class CPU {
    var ip = 0
    var acc = 0
    var exitCode = -1
    var finished = false
}

enum class OpCodes {
    nop, jmp, acc, exit, invalid
}
fun getOpCode(input: String): OpCodes {
    val op = when (input) {
        "nop" -> OpCodes.nop
        "jmp" -> OpCodes.jmp
        "acc" -> OpCodes.acc
        "exit" -> OpCodes.exit
        else -> OpCodes.invalid
    }
    assert(op != OpCodes.invalid)
    return op
}

class Instruction(codeLine: String, val line: Int) {
    var executeCounter = 0
    var opcode = OpCodes.nop
    var arg: Int = 0

    init {
        val (strOpcode, strArg) = codeLine.split(" ")
        arg = strArg.toInt()
        opcode = getOpCode(strOpcode)
    }

    override fun toString(): String = "$line: ${opcode.name} $arg"

    fun switchOp(): Boolean {
        val op = opcode
        opcode = when (op) {
            OpCodes.nop -> OpCodes.jmp
            OpCodes.jmp -> OpCodes.nop
            else -> return false
        }
        return true
    }

    fun execute(cpu: CPU, showDebug: Boolean): Boolean {
        if (showDebug) {
            println("acc=${cpu.acc}, ip=${cpu.ip}, exec ${line}, op=${opcode.name} $arg")
        }

        if (executeCounter==0) {
            when (opcode) {
                OpCodes.nop -> cpu.ip++
                OpCodes.jmp -> cpu.ip += arg
                OpCodes.acc -> { cpu.ip++; cpu.acc += arg }
                OpCodes.exit -> { cpu.exitCode = arg; cpu.finished = true }
                OpCodes.invalid -> { cpu.exitCode = 255; cpu.finished = true }
            }
        }
        executeCounter++
        return (executeCounter == 1 || cpu.finished)
    }
}

class Code(linesOfCode: List<String>) {
    private var code = arrayOf<Instruction>()
    private var lastInstruction: Instruction? = null
    private var switchInstruction: Instruction? = null

    init {
        val codeList = mutableListOf<Instruction>()
        for ((nr, line) in linesOfCode.withIndex()) {
            val instr = Instruction(line, nr)
            codeList.add(instr)
        }
        codeList.add(Instruction("exit 0", codeList.size))
        code = codeList.toTypedArray()
    }

    fun run(cpu: CPU, showDebug: Boolean = true): Int {
        var success = true
        while (success && !cpu.finished) {
            val instr = code[cpu.ip]
            lastInstruction = instr
            success = instr.execute(cpu, showDebug)
        }
        return cpu.exitCode
    }

    fun findProblem(): Boolean {
        for (idx in code.indices) {
            if (!switchInstruction(idx)) continue

            println("try on idx $idx")
            val cpu = CPU()
            reset()
            run(cpu)
            if (lastInstruction?.opcode == OpCodes.exit) {
                println("found solution idx $idx")
                println("acc ${cpu.acc}, code size ${code.size}, line ${getLastLine()}")
                return true
            } else {
                println("failed at ip ${cpu.ip}, op ${getLastLine()}")
            }
        }
        return false
    }

    private fun switchInstruction(line: Int): Boolean {
        val newInstruction = code[line]
        if (newInstruction.switchOp()) {
            switchInstruction?.switchOp() // switch back again
            switchInstruction = newInstruction
            return true
        }
        return false
    }

    fun reset() {
        code.forEach { it.executeCounter = 0 }
        lastInstruction = null
    }

    fun getLastLine(): String = lastInstruction?.toString() ?: "invalid lastInstruction"
}

fun main() {
    val code = Code(readInput(8, 2020))

    val cpu = CPU()
    val exitCode = code.run(cpu)
    println("acc: ${cpu.acc}, ip: ${cpu.ip}, lastLine: ${code.getLastLine()}, exitCode: $exitCode")

    code.reset()
    code.findProblem()
}