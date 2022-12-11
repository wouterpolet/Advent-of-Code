package aoc2021.day25

import plus
import readInputAsLines

val year = 2021
val day = 25

fun main() {

    fun part1(input: List<String>): Int {
        var newSouth = (0 until input.size).flatMap { y -> (0 until input[y].length).map { x -> if (input[y][x] == 'v') Pair(x, y) else null }.filterNotNull() }.toSet()
        var newEast = (0 until input.size).flatMap { y -> (0 until input[y].length).map { x -> if (input[y][x] == '>') Pair(x, y) else null }.filterNotNull() }.toSet()
        val height = input.size
        val width = input[0].length

        var south = emptySet<Pair<Int, Int>>()
        var east = emptySet<Pair<Int, Int>>()

        fun getCoordinate(p: Pair<Int, Int>) = Pair(p.first % width, p.second % height)

        var count = 0

        while (newSouth != south || newEast != east) {
            east = newEast
            south = newSouth
            newSouth = mutableSetOf()
            newEast = mutableSetOf()
            var taken = south + east
            for (e in east) {
                if (!(getCoordinate(e + Pair(1, 0)) in taken)) {
                    newEast.add(getCoordinate(e + Pair(1, 0)))
                } else {
                    newEast.add(e)
                }
            }
            taken = south + newEast
            for (s in south) {
                if (!(getCoordinate(s + Pair(0, 1)) in taken)) {
                    newSouth.add(getCoordinate(s + Pair(0, 1)))
                } else {
                    newSouth.add(s)
                }
            }
            count++
        }

        return count
    }

    val testInput = readInputAsLines(year, day, Input.Test)
    check(part1(testInput) == 58)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
}
