package aoc2021.day17

import plus
import readInputAsLines
import kotlin.math.abs

val year = 2021
val day = 17

fun main() {
    fun reachesTarget(x: Int, y: Int, tXMin: Int, tXMax: Int, tYMin: Int, tYMax: Int): Boolean {
        var pos = Pair(0, 0)
        var speed = Pair(x, y)
        while (pos.second >= tYMin && pos.first <= tXMax) {
            if (pos.first in tXMin..tXMax && pos.second in tYMin..tYMax) {
                return true
            }
            pos += speed
            speed += Pair(if (speed.first != 0) -speed.first / abs(speed.first) else 0, -1)
        }
        return false
    }

    fun maxHeight(y: Int): Int {
        var height = 0
        var speed = y
        while (speed > 0) {
            height += speed
            speed--
        }
        return height
    }

    fun part1(input: List<String>): Int {
        val (_, _, xRange, yRange) = input.first().split(" ")
        val (xMin, xMax) = xRange.split(",")[0].split("=")[1].split("..").map { it.toInt() }
        val (yMin, yMax) = yRange.split(",")[0].split("=")[1].split("..").map { it.toInt() }
        var maxHeight = 0
        for (y in 1..99) {
            for (x in 1..99) {
                val max = maxHeight(y)
                if (max > maxHeight && reachesTarget(x, y, xMin, xMax, yMin, yMax)) {
                    maxHeight = max
                }
            }
        }
        return maxHeight
    }

    fun part2(input: List<String>): Int {
        val (_, _, xRange, yRange) = input.first().split(" ")
        val (xMin, xMax) = xRange.split(",")[0].split("=")[1].split("..").map { it.toInt() }
        val (yMin, yMax) = yRange.split(",")[0].split("=")[1].split("..").map { it.toInt() }
        val valid = emptySet<Pair<Int, Int>>().toMutableSet()
        for (y in -300..300) {
            for (x in 0..300) {
                if (reachesTarget(x, y, xMin, xMax, yMin, yMax)) {
                    valid.add(Pair(x, y))
                }
            }
        }
        return valid.size
    }

    val testInput = readInputAsLines(year, day, Input.Test)
    check(part1(testInput) == 45)
//    check(part2(testInput) == 1)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
