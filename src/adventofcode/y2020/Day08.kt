package adventofcode.y2020

import adventofcode.readInput

class CPU() {
    var ip = 0
    var acc = 0
    var exitCode = -1
    var finished = false
    var program: List<Instruction>? = null

    fun reset() {
        ip = 0
        acc = 0
        exitCode = -1
        finished = false
    }

    init {
        reset()
    }

    companion object {
        private val cpu = CPU()

        fun getCPU(): CPU = cpu
    }

}

abstract class Operation(open val line: Int) {
    abstract fun execute(cpu: CPU): Boolean
    abstract fun toCode(): String
}

class OperationNop(override val line: Int, val arg: Int): Operation(line) {
    override fun execute(cpu: CPU): Boolean {
        cpu.ip++
        return true
    }
    override fun toCode(): String {
        return "nop $arg"
    }
}


class OperationExit(override val line: Int, val arg: Int): Operation(line) {
    override fun execute(cpu: CPU): Boolean {
        cpu.exitCode = arg
        cpu.finished = true
        return true
    }

    override fun toCode(): String {
        return "exit $arg"
    }
}

class OperationAcc(override val line: Int, val arg: Int): Operation(line) {
    override fun execute(cpu: CPU): Boolean {
        cpu.acc += arg
        cpu.ip++
        return true
    }
    override fun toCode(): String {
        return "acc $arg"
    }
}

class OperationJmp(override val line: Int, val arg: Int): Operation(line) {
    override fun execute(cpu: CPU): Boolean {
        cpu.ip += arg
        return true
    }
    override fun toCode(): String {
        return "jmp $arg"
    }
}

class Instruction(codeLine: String, private val line: Int) {
    var executeCounter = 0
    var operation: Operation = OperationNop(line, 0)

    init {
        val (opcode, arg) = codeLine.split(" ")
        val argInt = arg.toInt()

        operation = when (opcode) {
            "nop" -> OperationNop(line, argInt)
            "acc" -> OperationAcc(line, argInt)
            "jmp" -> OperationJmp(line, argInt)
            "exit" -> OperationExit(line, argInt)
            else -> operation // no change
        }
    }

    fun switchOp() {
        val op = operation
        if (op is OperationNop) {
            operation = OperationJmp(op.line, op.arg)
        }
        else if (op is OperationJmp) {
            operation = OperationNop(op.line, op.arg)
        }
    }

    fun execute(cpu: CPU, showDebug: Boolean): Boolean {
        if (showDebug) {
            println("acc=${cpu.acc}, ip=${cpu.ip}, exec ${line}, op=${operation.toCode()}")
        }

        if (executeCounter==0) {
            if(! operation.execute(cpu)) return false
        }
        executeCounter++
        return (executeCounter == 1)
    }
}

class Code(private val linesOfCode: List<String>) {
    private var code = arrayOf<Instruction>()
    var lastInstruction: Instruction? = null
    var switchInstruction: Instruction? = null

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

    fun searchProblem(cpu: CPU): Boolean {
        for (idx in code.indices) {
            if (!switchInstruction(idx)) continue

            println("try on idx $idx")
            run(cpu)
            if (lastInstruction?.operation is OperationExit) {
                println("found solution idx $idx")
                println("acc ${cpu.acc}, code size ${code.size}, line ${lastLine}")
                return true
            } else {
                println("failed at ip ${cpu.ip}")
            }
            reset(cpu)
        }
        return false
    }

    fun switchInstruction(line: Int): Boolean {
        val newInstruction = code[line]
        if (newInstruction.operation is OperationJmp || newInstruction.operation is OperationNop) {
            switchInstruction?.switchOp()
            newInstruction.switchOp()
            switchInstruction = newInstruction
            return true
        }
        return false
    }

    fun reset(cpu: CPU) {
        cpu.reset()
        for (instr in code) {
            instr.executeCounter = 0
        }
        lastInstruction = null
    }

    val lastLine: Int
        get() {
            val lastI = lastInstruction
            if (lastI!=null) return lastI.operation.line
            return -1
        }
}

fun main() {
    val code = Code(readInput(8, 2020))

    val cpu = CPU.getCPU()
    val exitCode = code.run(cpu)
    val lastLine = code.lastLine

    println("acc: ${cpu.acc}, ip: ${cpu.ip}, lastLine: $lastLine, exitCode: $exitCode")

    code.reset(cpu)
    code.searchProblem(cpu)
}