package aoc2015.day04

import md5
import readInput

val year = 2015
val day = 4

fun main() {
    fun part1(input: List<String>): Int {
        for (i in 1..Int.MAX_VALUE) {
            if ((input.first() + i).md5().length <= 32 - 5)
                return i
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        for (i in 1..Int.MAX_VALUE) {
            if ((input.first() + i).md5().length <= 32 - 6)
                return i
        }
        return -1
    }

    val testInput = readInput(year, day, Input.Test)
    check(part1(testInput) == 609043)
//    check(part2(testInput) == 1)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
