package aoc2015.day02

import readInput

val year = 2015
val day = 2

fun main() {
    fun part1(input: List<String>): Int = input
            .map { it.split('x').map { it.toInt() } }
            .map { (l, w, h) -> listOf(l * w, w * h, h * l) }
            .map { 2 * it.sum() + it.minOrNull()!! }
            .sum()

    fun part2(input: List<String>): Int = input
            .map { it.split('x').map { it.toInt() } }
            .map { 2 * (it.sum() - it.maxOrNull()!!) + it.reduce { a, b -> a * b } }
            .sum()

//    val testInput = readInput(year, day, Input.Test)
//    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
