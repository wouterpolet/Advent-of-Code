package aoc2015.day05

import readInput

val year = 2015
val day = 5

fun main() {

    fun part1(input: List<String>): Int =
        input.count {
            it.count { "aeiou".toList().contains(it) } >= 3
                    && it.zipWithNext().any { (a, b) -> a == b }
                    && !it.contains("""ab|cd|pq|xy""".toRegex())
        }

    fun part2(input: List<String>): Int = input.count {
        it.zipWithNext { a, b -> "$a" + "$b" }.zip(IntRange(0, it.length)) { str, i ->
            Pair(str, it.substring(i + 2).zipWithNext { a, b -> "$a" + "$b" })
        }.any { (str, others) -> others.contains(str) }
                && it.zipWithNext { a, b -> "$a" + "$b" }
            .zip(it.substring(2).toList()) { a, b -> a + "$b" }
            .any { it.first() == it.last() }
    }

//    val testInput = readInput(year, day, Input.Test)
//    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
