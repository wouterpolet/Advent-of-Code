package aoc2021.day07

import readInputAsLines
import kotlin.math.abs
import java.lang.Integer.min
import java.lang.Long.min as minL

val year = 2021
val day = 7

fun main() {
    fun part1(input: List<String>): Int {
        val pos = input.first().split(',').map { it.toInt() }
        var best = 9999999
        for (i in 0..(pos.maxOrNull() ?: 1)) {
            val thing = pos.map { abs(it - i) }.sum()
            best = min(thing, best)
        }
        return best
    }

    fun part2(input: List<String>): Long {
        val pos = input.first().split(',').map { it.toInt() }
        var best = 999999999999999999
        for (i in 0..(pos.maxOrNull() ?: 1)) {
            val thing = pos.map { abs(it - i).toLong() }.map { (it * (it + 1)) / 2 }.sum()
            best = minL(thing, best)
        }
        return best
    }

//    val testInput = readInput(year, day, Input.Test)
//    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
