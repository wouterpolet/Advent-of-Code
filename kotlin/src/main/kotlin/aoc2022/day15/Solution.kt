package aoc2022.day15

import Solver
import kotlin.math.abs

fun manhatten(one: Pair<Int, Int>, two: Pair<Int, Int>): Int =
    abs(one.first - two.first) + abs(one.second - two.second)


object Day15Solver : Solver(2022, 15) {
    override fun solvePartOne(input: List<String>): Int {
        val y = if (input.size < 30) 10 else 2000000
        val parseRegex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()
        val sbPairs = input.map {
            val groups = parseRegex.matchEntire(it)!!.groupValues
            Pair(Pair(groups[1].toInt(), groups[2].toInt()), Pair(groups[3].toInt(), groups[4].toInt()))
        }

        val sensorBeacons = sbPairs.flatMap { listOf(it.first, it.second) }
        val biggestDist = sbPairs.maxOf { manhatten(it.first, it.second) }
        val minX = sensorBeacons.minOf { it.first } - biggestDist - 1
        val maxX = sensorBeacons.maxOf { it.first } + biggestDist + 1
        val sbd = sbPairs.map { Pair(it, manhatten(it.first, it.second)) }

        var count = 0
        for (x in minX..maxX) {
            if (sensorBeacons.contains(Pair(x, y))) continue
            if (sbd.any { manhatten(Pair(x, y), it.first.first) <= it.second })
                count++
        }

        return count
    }

    override fun solvePartTwoLong(input: List<String>): Long {
        val maxCoord = if (input.size < 30) 20 else 4000000
        val parseRegex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()
        val sbPairs = input.map {
            val groups = parseRegex.matchEntire(it)!!.groupValues
            Pair(Pair(groups[1].toInt(), groups[2].toInt()), Pair(groups[3].toInt(), groups[4].toInt()))
        }

        val sbd = sbPairs.map { Pair(it, manhatten(it.first, it.second)) }
        // 6 = 7 (max_dist - x_dist) * 2 + 1
        // 7 = 5
        // 8 = 3
        // 9 = 1 (max_dist - actual_dist) eraf halen
        var x = 0
        var y = 0
        while (true) {
            val sensor = sbd.find { manhatten(Pair(x, y), it.first.first) <= it.second }
                ?: return x.toLong() * 4000000L + y.toLong()
//            y += (sensor.second - abs(x - sensor.first.first.first)) * 2 + 1 - (sensor.second - manhatten(Pair(x, y), sensor.first.first))
            y += sensor.second - 2 * abs(x - sensor.first.first.first) + manhatten(Pair(x, y), sensor.first.first) + 1
            if (y > maxCoord) {
                y = 0
                x++
            }
        }
    }
}

fun main() {
    Day15Solver.runPartOneTest()
    Day15Solver.runPartTwoTest()

    Day15Solver.solve()
}
