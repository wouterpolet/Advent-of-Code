import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path

enum class Input(val path: String) {
    Real("input"),
    Test("test")
}

fun readInput(year: Int, day: Int, instance: Input) = readInput(year, day, instance.path)

/**
 * Reads lines from the given input txt file.
 */
fun readInput(year: Int, day: Int, name: String) = Path(
    "src",
    "main", "kotlin", "aoc$year", "day${if (day < 10) "0$day" else day}", "$name.txt"
).toFile().readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("md5").digest(toByteArray())).toString(16)

fun <T> List<T>.permutations(): List<List<T>> =
    if (size == 1)
        listOf(this)
    else
        map { x -> (this - x).permutations().map { listOf(x) + it } }.flatten()
