package aoc2021.day08

import readInputAsLines

val year = 2021
val day = 8

fun main() {
    fun part1(input: List<String>): Int = input
            .map { it.split(" | ")[1].split(' ') }
            .flatten()
            .count { it.length == 2 || it.length == 4 || it.length == 3 || it.length == 7}

    fun decode(configs: List<String>, digit: List<String>): Int {
        //  0000
        // 1    2
        // 1    2
        //  3333
        // 4    5
        // 4    5
        //  6666
        val chars = Array(7) { '.' }
        // Encodes the characters used by each digit
        val charSets = Array(10) { setOf<Char>() }
        // Unique
        charSets[1] = configs.filter { it.length == 2 }.first().toSet()
        charSets[7] = configs.filter { it.length == 3 }.first().toSet()
        charSets[4] = configs.filter { it.length == 4 }.first().toSet()
        charSets[8] = configs.filter { it.length == 7 }.first().toSet()
        // Diff between 7 and 1 is top bar
        chars[0] = (charSets[7] - charSets[1]).first()
        // Intersect of 2, 3, 5 is horizontal. Minus 4 and 7 becomes bottom bar
        chars[6] = (configs.filter { it.length == 5 }.map { it.toSet() }.reduce(Set<Char>::intersect) - charSets[4] - charSets[7]).first()
        // The middle bar is all horizontal, except top and bottom
        chars[3] = (configs.filter { it.length == 5 }.map { it.toSet() }.reduce(Set<Char>::intersect) - chars[0] - chars[6]).first()
        // The one from 0, 6, 9 without the middle is 0
        charSets[0] = configs.first { it.length == 6 && !it.contains(chars[3]) }.toSet()
        // From 6 and 9, 6 has only one in common with 1
        charSets[6] = (configs.filter { it.length == 6 }.map { it.toSet() }.toSet() - setOf(charSets[0])).filter { it.intersect(charSets[1]).size == 1 }.first()
        charSets[9] = (configs.filter { it.length == 6 }.map { it.toSet() }.toSet() - setOf(charSets[0], charSets[6])).first()
        // From 2, 3 and 5, only 3 has two in common with 1
        charSets[3] = configs.filter { it.length == 5 }.map { it.toSet() }.toSet().filter { it.intersect(charSets[1]).size == 2 }.first()
        // From 2 and 5, only 5 has 3 in common with 4
        charSets[5] = (configs.filter { it.length == 5 }.map { it.toSet() }.toSet() - setOf(charSets[3])).filter { it.intersect(charSets[4]).size == 3 }.first()
        // Only 2 remains
        charSets[2] = (configs.map { it.toSet() }.toSet() - setOf(*charSets)).first()


        val digitMap = mapOf(*charSets.mapIndexed { i, v -> Pair(v, i.toString()) }.toTypedArray() )

        return digit.map { digitMap[it.toSet()] }.joinToString("").toInt()
    }

    fun part2(input: List<String>): Int = input
        .map { it.split('|').map { it.split(' ').map { it.trim() }.filter { it.isNotEmpty() } } }
        .map { (config, digit) -> decode(config, digit) }
        .sum()

    val testInput = readInputAsLines(year, day, Input.Test)
//    check(part1(testInput) == 1)
    println(part2(testInput))
    check(part2(testInput) == 5353)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
