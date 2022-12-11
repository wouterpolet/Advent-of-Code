package aoc2015.day03

import readInputAsLines

val year = 2015
val day = 3

fun main() {
    fun part1(input: List<String>): Int = input.first()
        .scan(listOf(0, 0)) { loc, dir ->
            loc.zip(
                when (dir) {
                    '^' -> listOf(0, 1)
                    'v' -> listOf(0, -1)
                    '<' -> listOf(-1, 0)
                    '>' -> listOf(1, 0)
                    else -> listOf(0, 0)
                }
            ) { a, b -> a + b }
        }
        .distinct()
        .count()

    fun part2(input: List<String>): Int =
        (input.first()
            .filterIndexed { i, _ -> i % 2 == 0 }
            .scan(listOf(0, 0)) { loc, dir ->
                loc.zip(
                    when (dir) {
                        '^' -> listOf(0, 1)
                        'v' -> listOf(0, -1)
                        '<' -> listOf(-1, 0)
                        '>' -> listOf(1, 0)
                        else -> listOf(0, 0)
                    }
                ) { a, b -> a + b }
            }
        + input.first()
            .filterIndexed { i, _ -> i % 2 == 1 }
            .scan(listOf(0, 0)) { loc, dir ->
                loc.zip(
                    when (dir) {
                        '^' -> listOf(0, 1)
                        'v' -> listOf(0, -1)
                        '<' -> listOf(-1, 0)
                        '>' -> listOf(1, 0)
                        else -> listOf(0, 0)
                    }
                ) { a, b -> a + b }
            }
        )
            .distinct()
            .count()

//    val testInput = readInput(year, day, Input.Test)
//    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
