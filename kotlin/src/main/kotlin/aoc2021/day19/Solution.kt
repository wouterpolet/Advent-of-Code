package aoc2021.day19

import readInput
import kotlin.math.abs

val year = 2021
val day = 19

class Vector(val x: Int, val y: Int, val z: Int) {

    fun rotX() = Vector(x, -z, y)

    fun rotY() = Vector(z, y, -x)

    fun rotZ() = Vector(-y, x, z)

    fun manhattenDistance(other: Vector) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

    operator fun plus(o: Vector) = Vector(x + o.x, y + o.y, z + o.z)

    operator fun minus(o: Vector) = Vector(x - o.x, y - o.y, z - o.z)

    operator fun times(o: Vector) = Vector(x * o.x, y * o.y, z * o.z)

    override fun toString() = "$x,$y,$z"

    override fun equals(other: Any?) = (this === other) || ((other is Vector) && x == other.x && y == other.y && z == other.z)

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + z
        return result
    }
}

class BoundingBox(val beacons: List<Vector>) {

    fun overlaps(other: BoundingBox): Vector? {
        for (b in beacons) {
            for (bOther in other.beacons) {
                val t = b - bOther
                val overlapping = other.translate(t).intersect(beacons)
                if (overlapping.size >= 12) {
                    return t
                }
            }
        }
        return null
    }

    fun translate(v: Vector): List<Vector> = beacons.map { it + v }
}

class Scanner(val id: Int, val position: Vector?, val beacons: List<Vector>) {

    fun overlapping(other: Scanner) = boundingBox().overlaps(other.boundingBox())?.plus(position!!)

    fun boundingBox() = if (position != null) BoundingBox(beacons.map { it - position }) else BoundingBox(beacons)

    fun orientations(): List<Scanner> {
        val xPos = mutableListOf(this)
        val xMin = mutableListOf(this.rotY().rotY())
        val yMin = mutableListOf(this.rotZ())
        val yPos = mutableListOf(yMin.first().rotZ().rotZ())
        val zPos = mutableListOf(this.rotY())
        val zMin = mutableListOf(zPos.first().rotY().rotY())

        for (i in 1..3) {
            xPos.add(xPos.last().rotX())
            xMin.add(xMin.last().rotX())

            yPos.add(yPos.last().rotY())
            yMin.add(yMin.last().rotY())

            zPos.add(zPos.last().rotZ())
            zMin.add(zMin.last().rotZ())
        }

        return listOf(xPos, xMin, yPos, yMin, zPos, zMin).flatten()
    }

    fun setPosition(pos: Vector) = Scanner(id, pos, beacons.map { it + pos })

    fun rotX() = Scanner(id, position, beacons.map(Vector::rotX))

    fun rotY() = Scanner(id, position, beacons.map(Vector::rotY))

    fun rotZ() = Scanner(id, position, beacons.map(Vector::rotZ))

    override fun equals(other: Any?) = (this === other) || ((other is Scanner) && id == other.id && beacons == other.beacons)

    override fun toString() = "--- scanner $id ---\n${beacons.joinToString("\n")}"

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (position?.hashCode() ?: 0)
        result = 31 * result + beacons.hashCode()
        return result
    }
}

fun main() {
    var setScanners = mutableListOf<Scanner>()

    fun part1(input: List<String>): Int {
        val scanners = input.joinToString("\n")
            .split("\n\n")
            .map {
                val lines = it.lines()
                val id = lines.first().split(' ')[2].toInt()
                val beacons = lines.drop(1).map { it.split(',').map(String::toInt) }.map { (x, y, z) -> Vector(x, y, z) }
                Scanner(id, null, beacons)
            }
        setScanners = mutableListOf()
        setScanners.add(Scanner(0, Vector(0, 0, 0), scanners.first().beacons))
        val floatingScanners = scanners.drop(1).toMutableList()

        while (floatingScanners.isNotEmpty()) {
            var toSet: Scanner? = null
            for (scanner in setScanners) {
                if (toSet != null) break
                for (fScanner in floatingScanners.flatMap { it.orientations() }) {
                    val posTranslate = scanner.overlapping(fScanner)
                    if (posTranslate != null) {
                        toSet = fScanner.setPosition(posTranslate)
                        break
                    }
                }
            }
            if (toSet == null) {
                throw RuntimeException("No overlapping scanners found")
            } else {
                floatingScanners.removeIf { it.id == toSet!!.id }
                setScanners.add(toSet)
                toSet = null
                println("Number of set scanners: ${setScanners.size}")
            }
        }

        val result = setScanners.flatMap { it.beacons }.distinct().size
        return result
    }

    fun part2(): Int {
        var max = -1
        for (scanner in setScanners) {
            for (otherScanner in setScanners) {
                val distance = scanner.position!!.manhattenDistance(otherScanner.position!!)
                if (distance > max) {
                    max = distance
                }
            }
        }
        return max
    }

    val testInput = readInput(year, day, Input.Test)
    check(part1(testInput) == 79)
    check(part2() == 3621)

    val input = readInput(year, day, Input.Real)
    println("Day $day, part one: ${part1(input)}")
    println("Day $day, part two: ${part2()}")
}
