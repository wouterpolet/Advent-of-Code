package aoc2021.day15

import plus
import readInputAsLines
import java.util.*

val year = 2021
val day = 15

fun main() {
    fun part1(input: List<String>): Int {
        val grid = input.map { it.map { it.digitToInt() } }
        val q: PriorityQueue<Pair<Int, List<Pair<Int, Int>>>> = PriorityQueue({ p1, p2 -> compareValues(p1.first, p2.first) })
        q.add(Pair(0, listOf(Pair(0, 0))))
        val destination = Pair(grid.size - 1, grid.size - 1)
        val distMap = emptyMap<Pair<Int, Int>, Int>().toMutableMap()
        while (q.isNotEmpty()) {
            val (curr, path) = q.poll()
            val coord = path.last()

            if ((distMap[coord] ?: Int.MAX_VALUE) < curr) {
                continue
            }

            for (dir in listOf(Pair(-1, 0), Pair(0, -1), Pair(1, 0), Pair(0, 1))) {
                val next = coord + dir
                val (x, y) = next
                if (x >= 0 && x < grid.size && y >= 0 && y < grid.size) {
                    if (curr + grid[y][x] < (distMap[next] ?: Int.MAX_VALUE)) {
                        distMap[next] = curr + grid[y][x]
                        q.add(Pair(curr + grid[y][x], path + next))
                    }
                }
            }
        }
        return distMap[destination]!!
    }

    fun part2(input: List<String>): Int {
        val smallGrid = input.map { it.map { it.digitToInt() } }
        val q: PriorityQueue<Pair<Int, Pair<Int, Int>>> = PriorityQueue { p1, p2 -> compareValues(p1.first, p2.first) }
        q.add(Pair(0, Pair(0, 0)))

        val row = smallGrid.map { (1 until 5).fold(it) { acc, i -> acc + it.map { if (it + i < 10) it + i else it + i - 9 } } }
        val grid = (1 until 5).fold(row) { acc, i -> acc + row.map { it.map { if (it + i < 10) it + i else it + i - 9 } } }.map { it.toIntArray() }.toTypedArray()

        val destination = Pair(grid.size - 1, grid.size - 1)
        val distMap = emptyMap<Pair<Int, Int>, Int>().toMutableMap()
        while (q.isNotEmpty()) {
            val (curr, coord) = q.poll()

            if ((distMap[coord] ?: Int.MAX_VALUE) < curr) {
                continue
            }

            for (dir in listOf(Pair(-1, 0), Pair(0, -1), Pair(1, 0), Pair(0, 1))) {
                val next = coord + dir
                val (x, y) = next
                if (x >= 0 && x < grid.size && y >= 0 && y < grid.size) {
                    if (curr + grid[y][x] < (distMap[next] ?: Int.MAX_VALUE)) {
                        distMap[next] = curr + grid[y][x]
                        q.add(Pair(curr + grid[y][x], next))
                    }
                }
            }
        }
        return distMap[destination]!!
    }

    val testInput = readInputAsLines(year, day, Input.Test)
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
