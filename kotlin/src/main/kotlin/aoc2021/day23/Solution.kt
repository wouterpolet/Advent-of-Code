package aoc2021.day23

import plus
import readInput
import java.util.*

val year = 2021
val day = 23

fun main() {

    val hallway = (0..10).map { Pair(it, 0) }.toSet()
    var amphipodRooms = mapOf(
        'A' to setOf(Pair(2, 1), Pair(2, 2)),
        'B' to setOf(Pair(4, 1), Pair(4, 2)),
        'C' to setOf(Pair(6, 1), Pair(6, 2)),
        'D' to setOf(Pair(8, 1), Pair(8, 2))
    )
    var rooms = amphipodRooms.values.flatten().toSet()
    val energies = mapOf(
        'A' to 1,
        'B' to 10,
        'C' to 100,
        'D' to 1000
    )

    // Create string key of map to search more efficiently
    fun mapToKey(positions: Map<Char, Set<Pair<Int, Int>>>): String {
        var key = ""
        for (x in 0..10) {
            var added = false
            for ((c, v) in positions.entries) {
                if (Pair(x, 0) in v) {
                    key += c
                    added = true
                }
            }
            if (!added) {
                key += '.'
            }
            if (x in 2..8 && x % 2 == 0) {
                for (y in 1..4) {
                    added = false
                    for ((c, v) in positions.entries) {
                        if (Pair(x, y) in v) {
                            key += c
                            added = true
                        }
                    }
                    if (!added) {
                        key += '.'
                    }
                }
            }
        }
        return key
    }

    fun findAdjacent(positions: Map<Char, Set<Pair<Int, Int>>>, start: Pair<Int, Int>, current: Pair<Int, Int>, amphipod: Char, visited: Set<Pair<Int, Int>>): List<Pair<Int, Pair<Int, Int>>> {
        val possibilities = mutableListOf<Pair<Int, Pair<Int, Int>>>()
        for (offset in listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))) {
            val newLoc = current + offset
            // Non-existing tile
            if (!(newLoc in hallway || newLoc in rooms)) continue
            // Cannot go through other amphipod
            if (newLoc in positions.values.flatten()) continue
            // Should not revisit same positions
            if (newLoc in visited) continue
            // Going into wrong room won't help
            if (current in hallway && newLoc in (rooms - amphipodRooms[amphipod]!!)) continue

            // Add recursive call result to other list
            possibilities.addAll(findAdjacent(positions, start, newLoc, amphipod, visited + current).map { (cost, pos) -> Pair(cost + energies[amphipod]!!, pos) })

            // Immediately outside a room, do not add current tile
            if (newLoc == Pair(2, 0) || newLoc == Pair(4, 0) || newLoc == Pair(6, 0) || newLoc == Pair(8, 0)) continue
            // Correct room, but other amphipod is there
            if (newLoc in rooms && positions.filterKeys { it != amphipod }.values.flatten().any { it in amphipodRooms[amphipod]!! })
                continue
            // From hallway, not to other hallway
            if (start in hallway && newLoc in hallway) continue

            // Add current with cost to the list
            possibilities.add(Pair(energies[amphipod]!!, newLoc))
        }
        return possibilities
    }

    fun solution(input: List<String>): Int {

        val amphipods = input
            .flatMapIndexed { y, line ->
                line.mapIndexed {
                        x, char -> if (char < 'A' || char > 'D') null else Pair(x, y)
                }
            }.filterNotNull().groupBy { (x, y) -> input[y][x] }.mapValues { it.value.map { it + Pair(-1, -1) }.toSet() }

        val q = PriorityQueue<Pair<Int, Map<Char, Set<Pair<Int, Int>>>>> { p1, p2 -> compareValues(p1.first, p2.first) }
        q.add(Pair(0, amphipods))

        val costs = mutableMapOf(mapToKey(amphipods) to 0)

        while (!q.isEmpty()) {
            val (cost, positions) = q.remove()
            if (costs[mapToKey(positions)]!! < cost)
                continue
            for (amphipod in positions.keys) {
                // Not move out of fully correct room
                if (amphipodRooms[amphipod]!! == positions[amphipod]!!) {
                    continue
                }
                for (pos in positions[amphipod]!!) {
                    val adj = findAdjacent(positions, pos, pos, amphipod, emptySet())
                    for ((stepCost, newLoc) in adj) {
                        val newCost = cost + stepCost
                        val newMap = positions.toMutableMap()
                        newMap[amphipod] = positions[amphipod]!! - pos + newLoc
                        val newKey = mapToKey(newMap)
                        if ((costs[newKey] ?: Int.MAX_VALUE) > newCost) {
                            costs[newKey] = newCost
                            q.add(Pair(newCost, newMap))
                        }
                    }
                }
            }
        }

        return costs[mapToKey(amphipodRooms)] ?: -1
    }

    val testInput = readInput(year, day, Input.Test)
    val testPart1 = solution(testInput)
    println("Part 1 test output: $testPart1")
    check(testPart1 == 12521)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${solution(input)}")

    amphipodRooms = mapOf(
        'A' to setOf(Pair(2, 1), Pair(2, 2), Pair(2, 3), Pair(2, 4)),
        'B' to setOf(Pair(4, 1), Pair(4, 2), Pair(4, 3), Pair(4, 4)),
        'C' to setOf(Pair(6, 1), Pair(6, 2), Pair(6, 3), Pair(6, 4)),
        'D' to setOf(Pair(8, 1), Pair(8, 2), Pair(8, 3), Pair(8, 4))
    )
    rooms = amphipodRooms.values.flatten().toSet()


    val testInput2 = readInput(year, day, "test2")
    val testPart2 = solution(testInput2)
    println("Part 2 test output: $testPart2")
    check(testPart2 == 44169)

    val input2 = readInput(year, day, "input2")
    println("Day $day, part two: ${solution(input2)}")
}
