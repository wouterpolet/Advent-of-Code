package aoc2022.day17

import Solver
import plus
import kotlin.math.max

object Day17Solver : Solver(2022, 17) {

    val shapes = listOf(
        listOf(
            Pair(0, 0),
            Pair(1, 0),
            Pair(2, 0),
            Pair(3, 0)
        ),
        listOf(
            Pair(0, 1),
            Pair(1, 2),
            Pair(1, 1),
            Pair(1, 0),
            Pair(2, 1)
        ),
        listOf(
            Pair(0, 0),
            Pair(1, 0),
            Pair(2, 0),
            Pair(2, 1),
            Pair(2, 2)
        ),
        listOf(
            Pair(0, 0),
            Pair(0, 1),
            Pair(0, 2),
            Pair(0, 3)
        ),
        listOf(
            Pair(0, 0),
            Pair(0, 1),
            Pair(1, 0),
            Pair(1, 1)
        )
    )

    fun validPosition(pos: Pair<Int, Int>, shape: List<Pair<Int, Int>>, occupied: Set<Pair<Int, Int>>): Boolean =
        shape.all {
            val c = pos + it
            !occupied.contains(c) && c.first > 0 && c.first <= 7 && c.second > 0
        }

    fun validBigPosition(pos: Pair<Int, Long>, shape: List<Pair<Int, Int>>, occupied: Set<Pair<Int, Long>>, floor: Long): Boolean =
        shape.all {
            val c = Pair(pos.first + it.first, pos.second + it.second)
            !occupied.contains(c) && c.first > 0 && c.first <= 7 && c.second > floor
        }

    fun printChamber(maxY: Int, occupied: Set<Pair<Int, Int>>, current: List<Pair<Int, Int>>, floor: Int) {
        for (r in maxY + 8 downTo floor + 1) {
            println("|" + (1..7).map { if (occupied.contains(Pair(it, r))) '#' else if (current.contains(Pair(it, r))) '@' else '.' }.joinToString("") + "|")
        }
        println("+-------+\n")
    }

    override fun solvePartOne(input: List<String>): Int {
        val jet = input.first().map { if (it == '<') -1 else 1 }
        val startX = 3
        var maxY = 0
        val occupied: MutableSet<Pair<Int, Int>> = (1..7).map { Pair(it, 0) }.toMutableSet()

        var jetIndex = 0

        for (i in 0 until 2022) {
            val shape = shapes[i % shapes.size]
            var pos = Pair(startX, maxY + 4)

            while (true) {
                val posAfterPush = Pair(pos.first + jet[jetIndex % jet.size], pos.second)
                jetIndex++
                if (validPosition(posAfterPush, shape, occupied)) {
                    pos = posAfterPush
                }

                val posAfterFall = Pair(pos.first, pos.second - 1)
                if (validPosition(posAfterFall, shape, occupied)) {
                    pos = posAfterFall
                } else {
                    occupied.addAll(shape.map { it + pos })
                    maxY = max(maxY, shape.maxOf { it.second } + pos.second)
                    if (i == 2021) {
                        printChamber(maxY, occupied, shape.map { it + pos }, 0)
                    }
                    break
                }
            }
        }

        return maxY
    }

    override fun solvePartTwoLong(input: List<String>): Long {
        return -1
    }
}

fun main() {
    Day17Solver.runPartOneTest()
    Day17Solver.runPartTwoTest()

    Day17Solver.solve()
}
