package aoc2021.day20

import plus
import readInputAsLines

val year = 2021
val day = 20

fun main() {
    fun printImage(lit: Map<Pair<Int, Int>, Boolean>) {
        val minX = lit.minOf { it.key.first }
        val minY = lit.minOf { it.key.second }
        val maxX = lit.maxOf { it.key.first }
        val maxY = lit.maxOf { it.key.second }

        for (y in minY - 1 .. maxY + 1) {
            for (x in minX - 1 .. maxX + 1) {
                if (Pair(x, y) in lit) {
                    print(if (lit[Pair(x, y)]!!) '#' else '.')
                } else {
                    print(if (y == minY - 1 || y == maxY + 1) '-' else '|')
                }
            }
            println()
        }
        println()
    }

    fun part1(input: List<String>): Int {
        val algorithm = input.first().map { it == '#' }
        val image = input.takeLastWhile { it.isNotBlank() }

        var lit = (image.indices).flatMap { y -> (0 until image[y].length).map { x -> Pair(x, y) } }.map { (x, y) -> Pair(Pair(x, y), image[y][x] == '#') }.toMap()

        for (i in 1..2) {
            val newLit = mutableMapOf<Pair<Int, Int>, Boolean>()
            for (y in -i until image.size + i) {
                for (x in -i until image[0].length + i) {
                    var bitstring = ""
                    for (offset in listOf(
                        Pair(-1, -1),
                        Pair(0, -1),
                        Pair(1, -1),
                        Pair(-1, 0),
                        Pair(0, 0),
                        Pair(1, 0),
                        Pair(-1, 1),
                        Pair(0, 1),
                        Pair(1, 1),
                    )) {
                        bitstring += if (lit[Pair(x, y) + offset] ?: ((i % 2) == 0 && algorithm[0])) '1' else '0'
                    }
                    newLit[Pair(x, y)] = algorithm[bitstring.toInt(2)]
                }
            }
            lit = newLit
            printImage(lit)
        }

        return lit.values.count { it }
    }

    fun part2(input: List<String>): Int {
        val algorithm = input.first().map { it == '#' }
        val image = input.takeLastWhile { it.isNotBlank() }

        var lit = (image.indices).flatMap { y -> (0 until image[y].length).map { x -> Pair(x, y) } }.map { (x, y) -> Pair(Pair(x, y), image[y][x] == '#') }.toMap()

        for (i in 1..50) {
            val newLit = mutableMapOf<Pair<Int, Int>, Boolean>()
            for (y in -i until image.size + i) {
                for (x in -i until image[0].length + i) {
                    var bitstring = ""
                    for (offset in listOf(
                        Pair(-1, -1),
                        Pair(0, -1),
                        Pair(1, -1),
                        Pair(-1, 0),
                        Pair(0, 0),
                        Pair(1, 0),
                        Pair(-1, 1),
                        Pair(0, 1),
                        Pair(1, 1),
                    )) {
                        bitstring += if (lit[Pair(x, y) + offset] ?: ((i % 2) == 0 && algorithm[0])) '1' else '0'
                    }
                    newLit[Pair(x, y)] = algorithm[bitstring.toInt(2)]
                }
            }
            lit = newLit
        }

        return lit.values.count { it }
    }

    val testInput = readInputAsLines(year, day, Input.Test)
    check(part1(testInput) == 35)
    check(part2(testInput) == 3351)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
