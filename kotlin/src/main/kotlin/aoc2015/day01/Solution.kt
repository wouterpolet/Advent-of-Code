package aoc2015.day01

import readInputAsLines

val year = 2015
val day = 1

fun main() {
    fun part1(input: List<String>): Int = input.first().count { it == '(' } - input.first().count { it == ')' }

    fun part2(input: List<String>): Int {
        var pos = 0
        input.first().forEachIndexed { index, c ->
            if (pos == -1) {
                return index
            }
            pos += if (c == '(') 1 else -1
        }
        return input.first().length
    }

//    val testInput = readInput(year, day, Input.Test)
//    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
