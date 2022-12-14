package aoc2022.day14

import Solver
import plus

fun addSand(grid: MutableMap<Pair<Int, Int>, Char>): Boolean {
    val maxY = grid.filterValues { it == '#' }.maxOf { it.key.second }
    var sandLoc = Pair(500, 0)
    while (sandLoc.second <= maxY) {
        if (!grid.containsKey(sandLoc + Pair(0, 1))) {
            sandLoc += Pair(0, 1)
        } else if (!grid.containsKey(sandLoc + Pair(-1, 1))) {
            sandLoc += Pair(-1, 1)
        } else if (!grid.containsKey(sandLoc + Pair(1, 1))) {
            sandLoc += Pair(1, 1)
        } else {
            grid[sandLoc] = 'o'
            return true
        }
    }
    return false
}

fun addSandTwo(grid: MutableMap<Pair<Int, Int>, Char>): Boolean {
    val maxY = grid.filterValues { it == '#' }.maxOf { it.key.second }
    var sandLoc = Pair(500, 0)
    while (sandLoc.second < maxY + 2) {
        if (sandLoc.second == maxY + 1) {
            grid[sandLoc] = 'o'
            return true
        } else if (!grid.containsKey(sandLoc + Pair(0, 1))) {
            sandLoc += Pair(0, 1)
        } else if (!grid.containsKey(sandLoc + Pair(-1, 1))) {
            sandLoc += Pair(-1, 1)
        } else if (!grid.containsKey(sandLoc + Pair(1, 1))) {
            sandLoc += Pair(1, 1)
        } else {
            grid[sandLoc] = 'o'
            return true
        }
    }
    return false
}

object Day14Solver : Solver(2022, 14) {
    override fun solvePartOne(input: List<String>): Int {
        val grid: MutableMap<Pair<Int, Int>, Char> = mutableMapOf()
        input.forEach {
            val coords = it.split(" -> ").map {
                val xy = it.split(",")
                Pair(xy[0].toInt(), xy[1].toInt())
            }
            var curr = coords.first()
            coords.drop(1).forEach {
                (curr.first..it.first).forEach { grid[Pair(it, curr.second)] = '#' }
                (it.first..curr.first).forEach { grid[Pair(it, curr.second)] = '#' }
                (curr.second..it.second).forEach { grid[Pair(curr.first, it)] = '#' }
                (it.second..curr.second).forEach { grid[Pair(curr.first, it)] = '#' }
                curr = it
            }
        }

        while (addSand(grid)) {}

        return grid.filterValues { it == 'o' }.size
    }

    override fun solvePartTwo(input: List<String>): Int {
        val grid: MutableMap<Pair<Int, Int>, Char> = mutableMapOf()
        input.forEach {
            val coords = it.split(" -> ").map {
                val xy = it.split(",")
                Pair(xy[0].toInt(), xy[1].toInt())
            }
            var curr = coords.first()
            coords.drop(1).forEach {
                (curr.first..it.first).forEach { grid[Pair(it, curr.second)] = '#' }
                (it.first..curr.first).forEach { grid[Pair(it, curr.second)] = '#' }
                (curr.second..it.second).forEach { grid[Pair(curr.first, it)] = '#' }
                (it.second..curr.second).forEach { grid[Pair(curr.first, it)] = '#' }
                curr = it
            }
        }

        while (addSandTwo(grid) && !grid.containsKey(Pair(500, 0))) {}

        return grid.filterValues { it == 'o' }.size
    }
}

fun main() {
    Day14Solver.runPartOneTest()
    Day14Solver.runPartTwoTest()

    Day14Solver.solve()
}
