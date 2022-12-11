package aoc2021.day24

import readInputAsLines

val year = 2021
val day = 24

class NoMoreInputException : Exception()

class ALU(val program: List<List<String>>) {
    fun compute(input: List<Int>): Int {
        val variables = mutableMapOf("w" to 0, "x" to 0, "y" to 0, "z" to 0)
        val currentInput = input.toMutableList()

        try {
            program.forEach {
                variables[it[1]] = when (it[0]) {
                    "inp" -> if (currentInput.isEmpty()) throw NoMoreInputException() else currentInput.removeFirst()
                    "add" -> variables[it[1]]!! + (variables[it[2]] ?: it[2].toInt())
                    "mul" -> variables[it[1]]!! * (variables[it[2]] ?: it[2].toInt())
                    "div" -> variables[it[1]]!! / (variables[it[2]] ?: it[2].toInt())
                    "mod" -> variables[it[1]]!! % (variables[it[2]] ?: it[2].toInt())
                    "eql" -> if (variables[it[1]]!! == (variables[it[2]] ?: it[2].toInt())) 1 else 0
                    else -> 0
                }
            }
        } catch (e: NoMoreInputException) {
            return variables["z"]!!
        }


        return variables["z"]!!
    }

//    fun hardCode(input: List<Int>): Int {
//
//    }
}

fun main() {
    fun part1(input: List<String>): Long {
        val alu = ALU(input.map { it.split(' ') })

        val model = "96918996924991"

        val ans = alu.compute(model.map { it.digitToInt() }).toLong()

        println("Div: ${ans / 26}")
        println("Remainder: ${ans % 26}")

        return ans
    }

    fun part2(input: List<String>): Long {
        val alu = ALU(input.map { it.split(' ') })

        val model = "91811241911641"

        val ans = alu.compute(model.map { it.digitToInt() }).toLong()

        println("Div: ${ans / 26}")
        println("Remainder: ${ans % 26}")

        return ans
    }

//    val testInput = readInput(year, day, Input.Test)
//    check(part1(testInput) == 0L)
//    check(part2(testInput) == 1)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
