package adventofcode.y2023

import adventofcode.generic.lcm

data class Step(val idx: Int, val name: String, val left: String, val right: String)

fun parseStep(idx: Int, line: String): Step = line.split(" = ", "(", ")", ", ").filter { it.isNotBlank() }
    .let { Step(idx, it[0], it[1], it[2]) }

class Day08(inputData: List<String>) {

    val instructions = inputData[0]
    val steps: Map<String, Step> =
        inputData.drop(2).mapIndexed { idx, line -> parseStep(idx, line).let { step -> step.name to step } }.toMap()

    fun execute(stepName: String, instruction: Char): String {
        val step = steps[stepName] ?: return ""
        return when (instruction) {
            'R' -> step.right
            'L' -> step.left
            else -> ""
        }
    }

    fun getExecutionCount(startStepName: String): Long {
        var currentStep = startStepName
        var stepCount = 0L
        var instructionCount = 0
        //println(currentStep)
        while (! currentStep.endsWith('Z')) {
            val instruction = instructions[instructionCount++]
            if (instructionCount >= instructions.length) instructionCount = 0

            currentStep = execute(currentStep, instruction)
            stepCount++
            //println(currentStep)
        }
        return stepCount
    }

    fun getSolution1(): Long = getExecutionCount("AAA")


    fun getStartSteps() = steps.keys.filter { it.endsWith("A") }

    fun getAllExecutionCounts(): List<Long> = getStartSteps().map { getExecutionCount(it) }

    fun getSolution2(): Long = getAllExecutionCounts().reduce { acc, l -> acc.lcm(l) }
}
