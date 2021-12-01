package aoc2021.day01

import readInput

val year = 2021
val day = 1

fun main() {
    fun part1(input: List<String>): Int = input
        .map { it.toInt() }
        .zipWithNext { a, b -> b > a }
        .count { it }

    fun part2(input: List<String>): Int = input
        .map { it.toInt() }
        .windowed(3) { it.sum() }
        .zipWithNext { a, b -> b > a }
        .count { it }

    val testInput = readInput(year, day, Input.Test)
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
