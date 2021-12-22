package aoc2021.day21

import plus
import readInput
import kotlin.math.max

val year = 2021
val day = 21

fun main() {
    fun part1(input: List<String>): Int {
        var p1 = Pair(input.first().split(": ").last().toInt(), 0)
        var p2 = Pair(input.last().split(": ").last().toInt(), 0)
        var dice = 0

        var player1Playing = true

        while (p1.second < 1000 && p2.second < 1000) {
            var toAdd = 0
            for (i in 1..3) {
                toAdd += ++dice
            }
            if (player1Playing) {
                var pos = (p1.first + toAdd) % 10
                if (pos == 0) {
                    pos = 10
                }
                val score = p1.second + pos

                p1 = Pair(pos, score)

                player1Playing = false
            } else {
                var pos = (p2.first + toAdd) % 10
                if (pos == 0) {
                    pos = 10
                }
                val score = p2.second + pos

                p2 = Pair(pos, score)

                player1Playing = true
            }
        }

        return dice * (if (p1.second < 1000) p1.second else p2.second)
    }

    val mem = mutableMapOf<List<Any>, Pair<Long, Long>>()

    fun wins(p1: Pair<Int, Int>, p2: Pair<Int, Int>, p1Playing: Boolean, currentCount: Int): Pair<Long, Long> {
        if (p1.second >= 21) {
            return Pair(1, 0)
        }
        if (p2.second >= 21) {
            return Pair(0, 1)
        }
        if (currentCount == 0) {
            val result = mem[listOf(p1, p2, p1Playing)]
            if (result != null) {
                return result
            }
        }
        var winsCount = Pair(0L, 0L)
        if (p1Playing) {
            if (currentCount == 3) {
                return wins(Pair(p1.first, p1.second + p1.first), p2, false, 0)
            } else {
                for (i in 1..3) {
                    var newPos = (p1.first + i) % 10
                    if (newPos == 0) {
                        newPos = 10
                    }
                    val newP1 = Pair(newPos, p1.second)
                    val thisWins = wins(newP1, p2, true, currentCount + 1)
                    winsCount = Pair(winsCount.first + thisWins.first, winsCount.second + thisWins.second)
                }
            }
        } else {
            if (currentCount == 3) {
                return wins(p1, Pair(p2.first, p2.second + p2.first), true, 0)
            } else {
                for (i in 1..3) {
                    var newPos = (p2.first + i) % 10
                    if (newPos == 0) {
                        newPos = 10
                    }
                    val newP2 = Pair(newPos, p2.second)
                    val thisWins = wins(p1, newP2, false, currentCount + 1)
                    winsCount = Pair(winsCount.first + thisWins.first, winsCount.second + thisWins.second)
                }
            }
        }
        if (currentCount == 0) {
            mem[listOf(p1, p2, p1Playing)] = winsCount
        }
        return winsCount
    }

    fun part2(input: List<String>): Long {
        val p1 = Pair(input.first().split(": ").last().toInt(), 0)
        val p2 = Pair(input.last().split(": ").last().toInt(), 0)

        val (p1Wins, p2Wins) = wins(p1, p2, true, 0)

        return max(p1Wins, p2Wins)
    }

    val testInput = readInput(year, day, Input.Test)
    check(part1(testInput) == 739785)
    check(part2(testInput) == 444356092776315)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
