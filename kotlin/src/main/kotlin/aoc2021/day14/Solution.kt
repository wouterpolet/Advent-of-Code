package aoc2021.day14

import readInputAsLines

val year = 2021
val day = 14

fun main() {
    fun part1(input: List<String>): Int {
        val template = input.first()
        val rules = mapOf(*input.takeLast(input.size - 2).map { it.split(" -> ") }.map { (a, b) -> Pair(a, b[0]) }.toTypedArray())

        var result = template.toMutableList()
        for (i in 1..10) {
            val pairs = result.zipWithNext { a, b -> "$a$b" }
            val newResult = mutableListOf(result[0])
            for (p in pairs) {
                newResult.add(rules[p]!!)
                newResult.add(p[1])
            }
            result = newResult
        }

        val max = result.groupBy { it }.map { it.value.size }.maxOrNull()!!
        val min = result.groupBy { it }.map { it.value.size }.minOrNull()!!

        return max - min
    }

    fun part2(input: List<String>): Long {
        val template = input.first()
        val rules = mapOf(*input.takeLast(input.size - 2).map { it.split(" -> ") }.map { (a, b) -> Pair(a, b[0]) }.toTypedArray())

        var polymer = mutableMapOf(*template.zipWithNext { a, b -> "$a$b" }.groupBy { it }.map { (a, b) -> Pair(a, b.size.toLong()) }.toTypedArray())

        for (i in 1..40) {
            val newPolymer = mutableMapOf<String, Long>()
            for ((p, amount) in polymer) {
                newPolymer["${p[0]}${rules[p]}"] = (newPolymer["${p[0]}${rules[p]}"] ?: 0) + amount
                newPolymer["${rules[p]}${p[1]}"] = (newPolymer["${rules[p]}${p[1]}"] ?: 0) + amount
            }
            polymer = newPolymer
        }

        val charCount = mutableMapOf<Char, Long>()
        for ((p, amount) in polymer) {
            charCount[p[0]] = (charCount[p[0]] ?: 0) + amount
        }
        charCount[template.last()] = (charCount[template.last()] ?: 0) + 1
        val max = charCount.values.maxOrNull()!!
        val min = charCount.values.minOrNull()!!

        return max - min
    }

//    val testInput = readInput(year, day, Input.Test)
//    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
