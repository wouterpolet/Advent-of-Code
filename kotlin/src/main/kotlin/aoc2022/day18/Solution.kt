package aoc2022.day18

import Solver


data class Cube(val x: Int, val y: Int, val z: Int)

object Day18Solver : Solver(2022, 18) {
    override fun solvePartOne(input: List<String>): Int {
        val cubes = input.map {
            val splitted = it.split(",")
            Cube(splitted[0].toInt(), splitted[1].toInt(), splitted[2].toInt())
        }
        var count = 0
        for (c in cubes) {
            var xNegAdj = false
            var xPosAdj = false
            var yNegAdj = false
            var yPosAdj = false
            var zNegAdj = false
            var zPosAdj = false
            for (other in cubes) {
                if (c == other) continue
                if (c.x - other.x == 1 && c.y == other.y && c.z == other.z) xPosAdj = true
                if (c.x - other.x == -1 && c.y == other.y && c.z == other.z) xNegAdj = true
                if (c.y - other.y == 1 && c.x == other.x && c.z == other.z) yPosAdj = true
                if (c.y - other.y == -1 && c.x == other.x && c.z == other.z) yNegAdj = true
                if (c.z - other.z == 1 && c.y == other.y && c.x == other.x) zPosAdj = true
                if (c.z - other.z == -1 && c.y == other.y && c.x == other.x) zNegAdj = true
            }
            if (!xPosAdj) count++
            if (!xNegAdj) count++
            if (!yPosAdj) count++
            if (!yNegAdj) count++
            if (!zPosAdj) count++
            if (!zNegAdj) count++
        }

        return count
    }

    override fun solvePartTwo(input: List<String>): Int {
        val drops = input.map {
            val splitted = it.split(",")
            Cube(splitted[0].toInt(), splitted[1].toInt(), splitted[2].toInt())
        }.toSet()
        val minX = drops.minOf { it.x }
        val maxX = drops.maxOf { it.x }
        val minY = drops.minOf { it.y }
        val maxY = drops.maxOf { it.y }
        val minZ = drops.minOf { it.z }
        val maxZ = drops.maxOf { it.z }

        val airPockets = mutableSetOf<Cube>()
        for (x in minX..maxX) {
            for (y in minY..maxY) {
                for (z in minZ..maxZ) {
                    if (drops.contains(Cube(x, y, z))) continue
                    val q = mutableListOf(Cube(x, y, z))
                    val visited = mutableSetOf(Cube(x, y, z))
                    var foundOutside = false
                    while (!q.isEmpty()) {
                        val curr = q.removeFirst()
                        if (curr.x < minX || curr.x > maxX || curr.y < minY || curr.y > maxY || curr.z < minZ || curr.z > maxZ) {
                            foundOutside = true
                            break
                        }
                        val paths = listOf(
                            Cube(curr.x - 1, curr.y, curr.z),
                            Cube(curr.x + 1, curr.y, curr.z),
                            Cube(curr.x, curr.y - 1, curr.z),
                            Cube(curr.x, curr.y + 1, curr.z),
                            Cube(curr.x, curr.y, curr.z - 1),
                            Cube(curr.x, curr.y, curr.z + 1),
                        )
                        for (next in paths) {
                            if (!drops.contains(next) && !visited.contains(next)) {
                                q.add(next)
                                visited.add(next)
                            }
                        }

                    }
                    if (!foundOutside) {
                        airPockets.add(Cube(x, y, z))
                    }
                }
            }
        }

        var count = 0
        for (c in drops) {
            val adjs = listOf(
                Cube(c.x - 1, c.y, c.z),
                Cube(c.x + 1, c.y, c.z),
                Cube(c.x, c.y - 1, c.z),
                Cube(c.x, c.y + 1, c.z),
                Cube(c.x, c.y, c.z - 1),
                Cube(c.x, c.y, c.z + 1),
            )
            for (adj in adjs) {
                if (!drops.contains(adj) && !airPockets.contains(adj)) count++
            }
        }
        return count
    }
}

fun main() {
    Day18Solver.runPartOneTest()
    Day18Solver.runPartTwoTest()

    Day18Solver.solve()
}
