package aoc2021.day05

import readInput

val year = 2021
val day = 5

fun main() {
    fun part1(input: List<String>): Int {
        val grid = List(1000) { List(1000) {0}.toMutableList() }
        val lines = input.map { it.split(" -> ").map { it.split(",").map { it.toInt() } } }

        for ((a, b) in lines) {
            var (x1, y1) = a
            var (x2, y2) = b

            if (x1 == x2) {
//                if (y1 > y2) {
//                    val temp = y2
//                    y2 = y1
//                    y1 = temp
//                }
                for (y in y1..y2) {
                    grid[x1][y]++
                }
            }
            if (y1 == y2) {
//                if (x1 > x2) {
//                    val temp = x2
//                    x2 = x1
//                    x1 = temp
//                }
                for (x in x1..x2) {
                    grid[x][y1]++
                }
            }
        }

        return grid.flatten().count { it >= 2}
    }

    fun part2(input: List<String>): Int {
        val grid = List(1000) { List(1000) {0}.toMutableList() }
        val lines = input.map { it.split(" -> ").map { it.split(",").map { it.toInt() } } }

        for ((a, b) in lines) {
            var (x1, y1) = a
            var (x2, y2) = b

            if (x1 == x2 || y1 == y2) {
                if (x1 == x2) {
                    if (y1 > y2) {
                        val temp = y2
                        y2 = y1
                        y1 = temp
                    }
                    for (y in y1..y2) {
                        grid[y][x1]++
                    }
                }
                if (y1 == y2) {
                    if (x1 > x2) {
                        val temp = x2
                        x2 = x1
                        x1 = temp
                    }
                    for (x in x1..x2) {
                        grid[y1][x]++
                    }
                }
            } else {
                if (x1 > x2) {
                    val tempX = x1
                    val tempY = y1
                    x1 = x2
                    y1 = y2
                    x2 = tempX
                    y2 = tempY
                }
                val up = y2 > y1
                var x = x1
                var y = y1
                while (x <= x2) {
                    grid[y][x]++
                    x++
                    if (up) y++ else y--
                }
            }

        }

        return grid.flatten().count { it >= 2}
    }

    val testInput = readInput(year, day, Input.Test)
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
