package aoc2015.day09

import permutations
import readInputAsLines
import kotlin.collections.HashMap

val year = 2015
val day = 9

fun main() {

    fun part1(input: List<String>): Int {
        val edges: Map<Set<String>, Int> = mapOf(*input.map {
            val (cities, distance) = it.split(" = ")
            cities.split(" to ").toSet() to distance.toInt()
        }.toTypedArray())
        val cities = edges.keys.flatten().toSet().toList()
        val possibilities = cities.permutations().toSet()
        val mem = HashMap<List<String>, Int>().toMutableMap()

        val distances = possibilities.map { xs ->
            val current = List(1){ xs[0] }.toMutableList()
            var dist = 0

            for (c in xs.subList(1, xs.size)) {
                current += c
                if (mem.containsKey(current)) {
                    dist = mem[current]!!
                } else {
                    dist += edges[current.takeLast(2).toSet()]!!
                    mem[current] = dist
                }
            }

            dist
        }

        return distances.minOrNull() ?: 0
    }

    fun part2(input: List<String>): Int {
        val edges: Map<Set<String>, Int> = mapOf(*input.map {
            val (cities, distance) = it.split(" = ")
            cities.split(" to ").toSet() to distance.toInt()
        }.toTypedArray())
        val cities = edges.keys.flatten().toSet().toList()
        val possibilities = cities.permutations().toSet()
        val mem = HashMap<List<String>, Int>().toMutableMap()

        val distances = possibilities.map { xs ->
            val current = List(1){ xs[0] }.toMutableList()
            var dist = 0

            for (c in xs.subList(1, xs.size)) {
                current += c
                if (mem.containsKey(current)) {
                    dist = mem[current]!!
                } else {
                    dist += edges[current.takeLast(2).toSet()]!!
                    mem[current] = dist
                }
            }

            dist
        }

        return distances.maxOrNull() ?: 0
    }

    val testInput = readInputAsLines(year, day, Input.Test)
    println(part1(testInput))
    check(part1(testInput) == 605)
//    check(part2(testInput) == 1)

    val input = readInputAsLines(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
