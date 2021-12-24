package aoc2021.day22

import readInput

val year = 2021
val day = 22

data class Coordinate(val x: Int, val y: Int, val z: Int)

data class Cube(val lower: Coordinate, val upper: Coordinate) {


    operator fun minus(other: Cube): List<Cube> {
        if (!overlaps(other)) {
            return listOf(this)
        }

        val result = mutableListOf<Cube>()

        val (outerLeft, innerRight) = splitX(other.lower.x - 1, other.lower.x)
        if (outerLeft.isValid())
            result.add(outerLeft)

        val (outerUp, innerDown) = innerRight.splitY(other.lower.y - 1, other.lower.y)
        if (outerUp.isValid())
            result.add(outerUp)

        val (outerDeep, innerInward) = innerDown.splitZ(other.lower.z - 1, other.lower.z)
        if (outerDeep.isValid())
            result.add(outerDeep)

        val (innerLeft, outerRight) = innerInward.splitX(other.upper.x, other.upper.x + 1)
        if (outerRight.isValid())
            result.add(outerRight)

        val (innerUp, outerDown) = innerLeft.splitY(other.upper.y, other.upper.y + 1)
        if (outerDown.isValid())
            result.add(outerDown)

        val (_, outerInward) = innerUp.splitZ(other.upper.z, other.upper.z + 1)
        if (outerInward.isValid() && !outerInward.overlaps(other))
            result.add(outerInward)


        return result
    }

    fun overlaps(other: Cube): Boolean {
        return (lower.x <= other.upper.x && lower.x >= other.lower.x) ||
                (lower.y <= other.upper.y && lower.y >= other.lower.y) ||
                (lower.z <= other.upper.z && lower.z >= other.lower.z) ||
                (upper.x <= other.upper.x && upper.x >= other.lower.x) ||
                (upper.y <= other.upper.y && upper.y >= other.lower.y) ||
                (upper.z <= other.upper.z && upper.z >= other.lower.z)
    }

    fun splitX(lowX: Int, highX: Int): Pair<Cube, Cube> {
        val dummy = Cube(Coordinate(0, 0, 0), Coordinate(-1, -1, -1))
        if (lowX < lower.x) {
            return Pair(dummy, this)
        }
        if (highX > upper.x) {
            return Pair(this, dummy)
        }
        val smaller = Cube(lower, Coordinate(lowX, upper.y, upper.z))
        val bigger = Cube(Coordinate(highX, lower.y, lower.z), upper)

        return Pair(smaller, bigger)
    }

    fun splitY(lowY: Int, highY: Int): Pair<Cube, Cube> {
        val dummy = Cube(Coordinate(0, 0, 0), Coordinate(-1, -1, -1))
        if (lowY < lower.y) {
            return Pair(dummy, this)
        }
        if (highY > upper.y) {
            return Pair(this, dummy)
        }

        val smaller = Cube(lower, Coordinate(upper.x, lowY, upper.z))
        val bigger = Cube(Coordinate(lower.x, highY, lower.z), upper)

        return Pair(smaller, bigger)
    }

    fun splitZ(lowZ: Int, highZ: Int): Pair<Cube, Cube> {
        val dummy = Cube(Coordinate(0, 0, 0), Coordinate(-1, -1, -1))
        if (lowZ < lower.z) {
            return Pair(dummy, this)
        }
        if (highZ > upper.z) {
            return Pair(this, dummy)
        }

        val smaller = Cube(lower, Coordinate(upper.x, upper.y, lowZ))
        val bigger = Cube(Coordinate(lower.x, lower.y, highZ), upper)

        return Pair(smaller, bigger)
    }

    fun isValid() = upper.x >= lower.x && upper.y >= lower.y && upper.z >= lower.z

    fun volume(): Long {
        return (upper.x - lower.x + 1).toLong() * (upper.y - lower.y + 1).toLong() * (upper.z - lower.z + 1).toLong()
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val lit = mutableSetOf<List<Int>>()
        input.forEach {
            val (switch, range) = it.split(' ')
            val (xs, ys, zs) = range.split(',').map { it.split('=')[1].split("..").map { it.toInt() } }
            if ((xs + ys + zs).all { it >= -50 && it <= 50 })
                for (x in xs[0]..xs[1]) {
                    for (y in ys[0]..ys[1]) {
                        for (z in zs[0]..zs[1]) {
                            if (switch == "on") {
                                lit.add(listOf(x, y, z))
                            } else {
                                lit.remove(listOf(x, y, z))
                            }
                        }
                    }
                }
        }
        return lit.size
    }

    fun part2(input: List<String>): Long {
        var cubes = listOf<Cube>()
        input.forEach {
            val (switch, range) = it.split(' ')
            val (xs, ys, zs) = range.split(',').map { it.split('=')[1].split("..").map { it.toInt() } }
            if ((xs + ys + zs).all { it >= -50 && it <= 50 }) {
                val newCube = Cube(Coordinate(xs[0], ys[0], zs[0]), Coordinate(xs[1], ys[1], zs[1]))
                cubes = cubes.flatMap { it - newCube } + (if (switch == "on") listOf(newCube) else emptyList())
            }
        }
        return cubes.sumOf { it.volume() }
    }

    val testInput = readInput(year, day, Input.Test)
    val part1Value = part1(testInput)
    println("Part 1 test: $part1Value")
//    check(part1(testInput) == 590784)
    val part2Value = part2(testInput)
    println("Part 2 test: $part2Value")
    check(part2Value == 590784.toLong())

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2(input)}")
}
