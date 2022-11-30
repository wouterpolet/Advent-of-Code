package aoc2021.day03

import readInput

val year = 2021
val day = 3

fun main() {
    fun part1(input: List<String>): Int {
        val lists = input.map { it.toList() }
        val bits = List(lists[0].size) { 0 }.toMutableList()

        for (i in 0 until lists[0].size) {
            for (str in lists) {
                if (str[i] == '1') {
                    bits[i]++;
                }
            }
        }

        val gamma = bits.map { if (it > input.size / 2) '1' else '0' }.joinToString("").toInt(2)
        val epsilon = bits.map { if (it > input.size / 2) '0' else '1' }.joinToString("").toInt(2)

        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        var i = 0
        val oxygenList = input.toMutableList()
        while (oxygenList.size > 1) {
            val zeroes = oxygenList.count { it[i] == '0' }
            val ones = oxygenList.count { it[i] == '1' }

            if (ones >= zeroes) {
                oxygenList.removeIf { it[i] == '0' }
            } else {
                oxygenList.removeIf { it[i] == '1' }
            }

            i++;
        }

        i = 0
        val carbonList = input.toMutableList()
        while (carbonList.size > 1) {
            val zeroes = carbonList.count { it[i] == '0' }
            val ones = carbonList.count { it[i] == '1' }

            if (zeroes > ones) {
                carbonList.removeIf { it[i] == '0' }
            } else {
                carbonList.removeIf { it[i] == '1' }
            }

            i++;
        }

        return oxygenList.first().toInt(2) * carbonList.first().toInt(2)
    }

    val testInput = readInput(year, day, Input.Test)
//    check(part1(testInput) == 1)
    check(part2(testInput) == 230)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
