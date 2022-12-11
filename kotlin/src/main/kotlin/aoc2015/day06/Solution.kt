package aoc2015.day06

import readInputAsLines

val year = 2015
val day = 6

fun main() {
    fun part1(input: List<String>): Int {
        val lights = MutableList(1000) { MutableList(1000) { false } }

        for (line in input) {
            if (line.startsWith("turn")) {
                val (_, c2, p1, _, p2) = line.split(' ')
                val (ltX, ltY) = p1.split(',')
                val (rbX, rbY) = p2.split(',')

                val value = c2 == "on"
                for (x in ltX.toInt()..rbX.toInt()) {
                    for (y in ltY.toInt()..rbY.toInt()) {
                        lights[x][y] = value
                    }
                }

            } else {
                val (_, p1, _, p2) = line.split(' ')
                val (ltX, ltY) = p1.split(',')
                val (rbX, rbY) = p2.split(',')

                for (x in ltX.toInt()..rbX.toInt()) {
                    for (y in ltY.toInt()..rbY.toInt()) {
                        lights[x][y] = !lights[x][y]
                    }
                }
            }
        }
        return lights.map{ it.count { it } }.sum()
    }

    fun part2(input: List<String>): Int {
        val lights = MutableList(1000) { MutableList(1000) { 0 } }

        for (line in input) {
            if (line.startsWith("turn")) {
                val (_, c2, p1, _, p2) = line.split(' ')
                val (ltX, ltY) = p1.split(',')
                val (rbX, rbY) = p2.split(',')

                val value = if (c2 == "on") 1 else -1
                for (x in ltX.toInt()..rbX.toInt()) {
                    for (y in ltY.toInt()..rbY.toInt()) {
                        lights[x][y] += value
                        if (lights[x][y] < 0)
                            lights[x][y] = 0
                    }
                }

            } else {
                val (_, p1, _, p2) = line.split(' ')
                val (ltX, ltY) = p1.split(',')
                val (rbX, rbY) = p2.split(',')

                for (x in ltX.toInt()..rbX.toInt()) {
                    for (y in ltY.toInt()..rbY.toInt()) {
                        lights[x][y] += 2
                    }
                }
            }
        }
        return lights.flatten().sum()
    }

//    val testInput = readInput(year, day, Input.Test)
//    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
