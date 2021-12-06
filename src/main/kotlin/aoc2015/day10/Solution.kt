package aoc2015.day10

import readInput

val year = 2015
val day = 10

fun main() {
    fun part1(input: List<String>): Int = (1..40).fold(input.first().map { it.digitToInt() }) { c, _ ->
        (c.subList(1, c.size) + -1).fold(listOf(c[0], 1, listOf<Int>())) { (a, t, acc), b ->
                if (a as Int == b) listOf(b, t as Int + 1, acc) else listOf(b, 1, acc as List<*> + listOf(t, a))
            }.last() as List<Int>
        }.size

    fun part2(input: List<String>): Int =
        (1..50).fold(input.first().map { it.digitToInt() }) { c, i ->
            println(i)
            (c.subList(1, c.size) + -1).fold(listOf(c[0], 1, listOf<Int>())) { (a, t, acc), b ->
                if (a as Int == b) listOf(b, t as Int + 1, acc) else listOf(b, 1, acc as List<*> + listOf(t, a))
            }.last() as List<Int>
        }.size

    val testInput = readInput(year, day, Input.Test)
//    println(part1(testInput))
//    check(part1(testInput) == 6)
//    check(part2(testInput) == 1)

    val input = readInput(year, day, Input.Real)
//    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
