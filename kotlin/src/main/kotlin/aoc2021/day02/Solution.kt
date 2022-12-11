package aoc2021.day02

import readInputAsLines

val year = 2021
val day = 2

fun main() {
    fun part1(input: List<String>): Int {
        var depth = 0
        var h = 0
        input.forEach {
            val (c, v) = it.split(' ')
            when (c) {
                "forward" -> h += v.toInt()
                "down" -> depth += v.toInt()
                "up" -> depth -= v.toInt()
            }
        }
        return depth * h
    }

    fun part1OneLine(input: List<String>): Int = input
        .map { it.split(' ') }
        .map { (a, b) -> Pair(a, b.toInt()) }
        .fold(listOf(0, 0)) { acc, (c, v) -> acc.zip(if (c == "forward") listOf(0, v) else if (c == "up") listOf(-v, 0) else listOf(v, 0)).map { (a, b) -> a + b} }
        .reduce(Int::times)

    fun part2(input: List<String>): Int {
        var depth = 0
        var h = 0
        var aim = 0
        input.forEach {
            val (c, v) = it.split(' ')
            when (c) {
                "forward" -> {
                    h += v.toInt()
                    depth += aim * v.toInt()
                }
                "down" -> {
                    aim += v.toInt()
                }
                "up" -> {
                    aim -= v.toInt()
                }
            }
        }
        return depth * h
    }

    fun part2OneLine(input: List<String>): Int = input
            .map { it.split(' ') }
            .map { (a, b) -> Pair(a, b.toInt()) }
            .fold(listOf(0, 0, 0)) { acc, (c, v) -> acc.zip(if (c == "forward") listOf(acc[2] * v, v, 0) else if (c == "up") listOf(0, 0, -v) else listOf(0, 0, v)).map { (a, b) -> a + b} }
            .subList(0, 2)
            .reduce(Int::times)


    val testInput = readInputAsLines(year, day, Input.Test)
//    check(part1(testInput) == 1)
    println(part2OneLine(testInput))
    check(part2(testInput) == 900)
    check(part2OneLine(testInput) == 900)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part one (one line): ${part1OneLine(input)}")
    println("Day $day, part two: ${part2(input)}")
    println("Day $day, part two (one line): ${part2OneLine(input)}")
}
