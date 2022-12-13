package aoc2022.day12

import Solver
import plus
import java.util.LinkedList
import java.util.Queue
import kotlin.streams.toList

object Day12Solver : Solver(2022, 12) {
    override fun solvePartOne(input: List<String>): Int {
        val grid = input.map { it.map { if (it == 'S') 'a' else if (it == 'E') 'z' else it } }
        val startY = (0 until input.size).find { input[it].contains("S") }!!
        val startX = input[startY].indexOf("S")
        val endY = (0 until input.size).find { input[it].contains("E") }!!
        val endX = input[endY].indexOf("E")
        val end = Pair(endX, endY)

        val visited: MutableSet<Pair<Int, Int>> = HashSet()
        val routeMap: MutableMap<Pair<Int, Int>, Pair<Int, Int>> = HashMap()
        val q: Queue<Pair<Int, Int>> = LinkedList()
        q.add(Pair(startX, startY))

        while (!q.isEmpty()) {
            val curr = q.poll()
            val currHeight = grid[curr.second][curr.first].code
            if (curr == end) {
                break
            }
            listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0)).forEach {
                val next = curr + it
                val nextHeight = grid.getOrNull(next.second)?.getOrNull(next.first)?.code
                if (nextHeight != null && !visited.contains(next) && !q.contains(next) && nextHeight - currHeight <= 1) {
                    q.add(next)
                    routeMap[next] = curr
                }
            }
            visited.add(curr)
        }

        var len = 0
        var pos = end
        while (routeMap.contains(pos)) {
            pos = routeMap[pos]!!
            len++
        }

        return len
    }

    override fun solvePartTwo(input: List<String>): Int {
        val grid = input.map { it.map { if (it == 'S') 'a' else if (it == 'E') 'z' else it } }
        val startY = (0 until input.size).find { input[it].contains("S") }!!
        val startX = input[startY].indexOf("S")
        val endY = (0 until input.size).find { input[it].contains("E") }!!
        val endX = input[endY].indexOf("E")
        val end = Pair(endX, endY)

        val allLocs = (0 until grid.size).flatMap { y -> (0 until grid[y].size).map { x -> Pair(x, y) } }
        val startingPositions = allLocs.filter { grid[it.second][it.first] == 'a' }

        return startingPositions.map {
            val visited: MutableSet<Pair<Int, Int>> = HashSet()
            val routeMap: MutableMap<Pair<Int, Int>, Pair<Int, Int>> = HashMap()
            val q: Queue<Pair<Int, Int>> = LinkedList()
            q.add(it)
            var found = false

            while (!q.isEmpty()) {
                val curr = q.poll()
                val currHeight = grid[curr.second][curr.first].code
                if (curr == end) {
                    found = true
                    break
                }
                listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0)).forEach {
                    val next = curr + it
                    val nextHeight = grid.getOrNull(next.second)?.getOrNull(next.first)?.code
                    if (nextHeight != null && !visited.contains(next) && !q.contains(next) && nextHeight - currHeight <= 1) {
                        q.add(next)
                        routeMap[next] = curr
                    }
                }
                visited.add(curr)
            }

            if (!found) {
                Integer.MAX_VALUE
            } else {
                var len = 0
                var pos = end
                while (routeMap.contains(pos)) {
                    pos = routeMap[pos]!!
                    len++
                }
                len
            }
        }.minOrNull() ?: -1
    }
}

fun main() {
//    Day12Solver.runPartOneTest()
//    Day12Solver.runPartTwoTest()

    Day12Solver.solve()
}
