package aoc2021.day11

import readInputAsLines

val year = 2021
val day = 11

fun main() {
    fun part1(input: List<String>): Int {
        val grid = input.map { it.map { it.digitToInt() }.toMutableList() }
        var flashes = 0
        for (i in 1..100) {
            grid.forEachIndexed { r, row -> row.forEachIndexed { c, v  -> grid[r][c] = v + 1 } }
            val flashed = emptySet<Pair<Int, Int>>().toMutableSet()
            do {
                val initSize = flashed.size
                for (r in 0 until grid.size) {
                    for (c in 0 until grid[r].size) {
                        if (grid[r][c] > 9 && !flashed.contains(Pair(r, c))) {
                            flashed.add(Pair(r, c))
                            flashes++

                            if (r > 0) grid[r-1][c]++
                            if (c > 0) grid[r][c-1]++
                            if (r > 0 && c > 0) grid[r-1][c-1]++
                            if (r + 1 < grid.size) grid[r+1][c]++
                            if (c + 1 < grid[r].size) grid[r][c+1]++
                            if (r + 1 < grid.size && c + 1 < grid[r].size) grid[r+1][c+1]++
                            if (r > 0 && c + 1 < grid[r].size) grid[r-1][c+1]++
                            if (r + 1 < grid.size && c > 0) grid[r+1][c-1]++
                        }
                    }
                }
            } while (initSize != flashed.size)
            flashed.forEach { (r, c) -> grid[r][c] = 0 }
        }
        return flashes
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { it.map { it.digitToInt() }.toMutableList() }
        for (i in 1..999) {
            grid.forEachIndexed { r, row -> row.forEachIndexed { c, v  -> grid[r][c] = v + 1 } }
            val flashed = emptySet<Pair<Int, Int>>().toMutableSet()
            do {
                val initSize = flashed.size
                for (r in 0 until grid.size) {
                    for (c in 0 until grid[r].size) {
                        if (grid[r][c] > 9 && !flashed.contains(Pair(r, c))) {
                            flashed.add(Pair(r, c))

                            if (r > 0) grid[r-1][c]++
                            if (c > 0) grid[r][c-1]++
                            if (r > 0 && c > 0) grid[r-1][c-1]++
                            if (r + 1 < grid.size) grid[r+1][c]++
                            if (c + 1 < grid[r].size) grid[r][c+1]++
                            if (r + 1 < grid.size && c + 1 < grid[r].size) grid[r+1][c+1]++
                            if (r > 0 && c + 1 < grid[r].size) grid[r-1][c+1]++
                            if (r + 1 < grid.size && c > 0) grid[r+1][c-1]++
                        }
                    }
                }
            } while (initSize != flashed.size)
            if (flashed.size == grid.sumOf { it.size }) {
                return i
            }
            flashed.forEach { (r, c) -> grid[r][c] = 0 }
        }
        return -1
    }

//    val testInput = readInput(year, day, Input.Test)
//    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
