package aoc2021.day12

import readInput
import java.util.*

val year = 2021
val day = 12

fun main() {
    fun part1(input: List<String>): Int {
        val q: Queue<Pair<String, List<String>>> = LinkedList()
        val edges = emptyMap<String, List<String>>().toMutableMap()
        val paths = emptySet<List<String>>().toMutableSet()
        input.map {
            it.split('-')
        }.forEach { (k, v) ->
            edges[k] = (edges[k] ?: emptyList()) + v
            edges[v] = (edges[v] ?: emptyList()) + k
        }
        q.add(Pair("start", listOf("start")))
        while (q.isNotEmpty()) {
            val (n, acc) = q.poll()
            if (n == "end") {
                paths.add(acc)
                continue
            }
            (edges[n] ?: emptyList()).forEach {
                if (it.all { it.isLowerCase() }) {
                    if (!acc.contains(it)) {
                        q.add(Pair(it, acc + it))
                    }
                } else {
                    q.add(Pair(it, acc + it))
                }
            }
        }
        return paths.size
    }

    fun part2(input: List<String>): Int {
        val q: Queue<Pair<String, List<String>>> = LinkedList()
        val edges = emptyMap<String, List<String>>().toMutableMap()
        val paths = emptySet<List<String>>().toMutableSet()
        input.map {
            it.split('-')
        }.forEach { (k, v) ->
            edges[k] = (edges[k] ?: emptyList()) + v
            edges[v] = (edges[v] ?: emptyList()) + k
        }
        q.add(Pair("start", listOf("start")))
        while (q.isNotEmpty()) {
            val (n, acc) = q.poll()
            if (n == "end") {
                paths.add(acc)
                continue
            }
            (edges[n] ?: emptyList()).forEach {
                if (it.all { it.isLowerCase() }) {
                    if (it != "start" &&
                        (acc.filter { x -> x == it }.size == 1 && acc.filter { it.all { it.isLowerCase() } }.sorted()
                            .zipWithNext().none { (a, b) -> a == b } || !acc.contains(it))) {
                        q.add(Pair(it, acc + it))
                    }
                } else {
                    q.add(Pair(it, acc + it))
                }
            }
        }
        return paths.size
    }

    val testInput = readInput(year, day, Input.Test)
    check(part1(testInput) == 10)
    check(part2(testInput) == 36)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
