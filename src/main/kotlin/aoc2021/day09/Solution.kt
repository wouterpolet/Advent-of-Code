package aoc2021.day09

import readInput

val year = 2021
val day = 9

fun main() {
    fun part1(input: List<String>): Int {
        val grid = input.map { it.toList().map { it.digitToInt() } }
        var risk = 0
        for (i in 0 until grid.size) {
            for (j in 0 until grid[i].size) {
                if ((i > 0 && grid[i-1][j] <= grid[i][j]) ||
                        (j > 0 && grid[i][j-1] <= grid[i][j]) ||
                        (i < grid.size - 1 && grid[i+1][j] <= grid[i][j]) ||
                        (j < grid[0].size - 1 && grid[i][j+1] <= grid[i][j])) {
                    continue
                }
                risk += grid[i][j] + 1
            }
        }
        return risk
    }

    fun part2(input: List<String>): Int {
        val basins = mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()
        val grid = input.map { it.toList().map { it.digitToInt() } }
        for (i in 0 until grid.size) {
            for (j in 0 until grid[i].size) {
                if (grid[i][j] == 9) {
                    continue
                }
                if (basins.containsKey(Pair(i - 1, j))) {
                    basins[Pair(i, j)] = basins[Pair(i - 1, j)]!!
                    basins[Pair(i, j)]!!.add(Pair(i, j))
                }
                if (basins.containsKey(Pair(i, j - 1))) {
                    if (basins.containsKey(Pair(i, j))) {
                        basins[Pair(i, j)]!!.addAll(basins[Pair(i, j - 1)]!!)
                    } else {
                        basins[Pair(i, j)] = basins[Pair(i, j - 1)]!!
                        basins[Pair(i, j)]!!.add(Pair(i, j))
                    }
                    basins[Pair(i, j)]!!.forEach { basins[it] = basins[Pair(i, j)]!! }
                }
                if (!basins.containsKey(Pair(i, j))) {
                    basins[Pair(i, j)] = mutableSetOf(Pair(i, j))
                }
            }
        }
        return basins.values.toSet().map { it.size }.sortedDescending().take(3).reduce(Int::times)
    }

    val testInput = readInput(year, day, Input.Test)
    check(part1(testInput) == 15)
    println(part2(testInput))
//    check(part2(testInput) == 1134)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
