package aoc2015.day08

import readInputAsLines

val year = 2015
val day = 8

fun main() {
    fun part1(input: List<String>): Int {
        val flattenedInput = input.flatMap { it.trim().toList() }
        var i = 1
        var count = input.size * 2
        while (i < flattenedInput.size - 1) {
            if (flattenedInput[i] == '\\') {
                when (flattenedInput[i + 1]) {
                    '\\' -> {
                        count++
                        i++
                    }
                    'x' -> {
                        count += 3
                        i += 3
                    }
                    '"' -> {
                        count++
                        i++
                    }
                }
            }
            i++
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val flatInput = input.flatMap { it.trim().toList() }
        return flatInput.sumOf { when (it) {
            '"' -> 1
            '\\' -> 1
            else -> 0
        }.toInt()} + input.size * 2
    }

    val testInput = readInputAsLines(year, day, Input.Test)
    println(part1(testInput))
    check(part1(testInput) == 12)
//    check(part2(testInput) == 1)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
