package aoc2021.day24

import readInput

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

        val answers = mutableMapOf<Int, Set<Long>>()

        val p1314 = (9 downTo 1).flatMap { i -> (9 downTo 1).map { j -> listOf(i, j) } }.filter { (i, j) -> i == j }
        val p1112 = (9 downTo 1).flatMap { i -> (9 downTo 1).map { j -> listOf(i, j) } }.filter { (i, j) -> j - i == 5 }
        val p910 = (9 downTo 1).flatMap { i -> (9 downTo 1).map { j -> listOf(i, j) } }.filter { (i, j) -> j - i == 6 }
        val p78 = (9 downTo 1).flatMap { i -> (9 downTo 1).map { j -> listOf(i, j) } }.filter { (i, j) -> i - j == 3 }
        val p56 = (9 downTo 1).flatMap { i -> (9 downTo 1).map { j -> listOf(i, j) } }.filter { (i, j) -> j - i == 1 }
        val p34 = (9 downTo 1).flatMap { i -> (9 downTo 1).map { j -> listOf(i, j) } }

        for (i in 99 downTo 11) {

            println(i)

            for (a in p34) {
                for (b in p56) {
                    for (c in p78) {
                        for (d in p910) {
                            for (e in p1112) {
                                for (f in p1314) {
                                    val inp = i.toString().map { it.digitToInt() } + a + b + c + d + e + f
                                    if (0 in inp) {
                                        continue
                                    }
                                    val ans = alu.compute(inp)
                                    answers[ans] = (answers[ans] ?: emptySet()) + i.toLong()
                                    if (ans == 0) {
                                        return inp.map { it.toString() }.joinToString("").toLong()
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        return -1
    }

    fun part2(input: List<String>): Int {
        return 0
    }

//    val testInput = readInput(year, day, Input.Test)
//    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
