package aoc2021.day06

import readInput

val year = 2021
val day = 6

fun main() {
    fun part1(input: List<String>): Int {
        val fish = input.first().split(',').map { listOf(it.toInt(), 6).toMutableList() }.toMutableList()
        for (i in 1..80) {
            val addFish = listOf<MutableList<Int>>().toMutableList()
            for (f in fish) {
                if (f[0] == 0) {
                    addFish.add(listOf(f[1] + 2, 6).toMutableList())
                    f[0] = f[1]
                } else {
                    f[0]--
                }
            }
            fish.addAll(addFish)
        }
        return fish.size
    }

    fun part2(input: List<String>): Long {
        val fish = HashMap<Int, Long>().toMutableMap()
        input.first().split(',').forEach { fish[it.toInt()] = fish.getOrDefault(it.toInt(), 0) + 1 }
        for (k in 1..256) {
            val temp = fish[0] ?: 0
            for (f in 1..8) {
                fish[f-1] = fish[f] ?: 0
            }
            fish[8] = temp
            fish[6] = (fish[6] ?: 0) + temp
        }
        return fish.map { (_, v) -> v }.sum()
    }

    val testInput = readInput(year, day, Input.Test)
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
