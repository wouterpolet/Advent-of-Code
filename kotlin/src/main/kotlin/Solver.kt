abstract class Solver(val year: Int, val day: Int) {

    companion object {
        val solvers: HashMap<Pair<Int, Int>, Solver> = HashMap()

        fun getSolver(year: Int, day: Int) = solvers.get(Pair(year, day))
    }

    init {
        solvers.put(Pair(year, day), this)
    }

    open fun solvePartOneString(input: List<String>): String = throw NotImplementedError()

    open fun solvePartOneString(input: String): String =
        try {
            solvePartOneString(input.lines())
        } catch (e: NotImplementedError) {
            solvePartOneLong(input).toString()
        }

    open fun solvePartOneString(input: Input): String = solvePartOneString(readInput(year, day, input))

    open fun solvePartOneLong(input: List<String>): Long = throw NotImplementedError()

    open fun solvePartOneLong(input: String): Long =
        try {
            solvePartOneLong(input.lines())
        } catch (e: NotImplementedError) {
            solvePartOne(input).toLong()
        }

    fun solvePartOneLong(input: Input): Long = solvePartOneLong(readInput(year, day, input))

    open fun solvePartOne(input: List<String>): Int = throw NotImplementedError()

    open fun solvePartOne(input: String): Int = solvePartOne(input.lines())

    fun solvePartOne(input: Input): Int = solvePartOne(readInput(year, day, input))

    open fun solvePartTwoString(input: List<String>): String = throw NotImplementedError()

    open fun solvePartTwoString(input: String): String =
        try {
            solvePartTwoString(input.lines())
        } catch (e: NotImplementedError) {
            solvePartTwoLong(input).toString()
        }

    fun solvePartTwoString(input: Input): String = solvePartTwoString(readInput(year, day, input))

    open fun solvePartTwoLong(input: List<String>): Long = throw NotImplementedError()

    open fun solvePartTwoLong(input: String): Long =
        try {
            solvePartTwoLong(input.lines())
        } catch (e: NotImplementedError) {
            solvePartTwo(input).toLong()
        }

    fun solvePartTwoLong(input: Input): Long = solvePartTwoLong(readInput(year, day, input))

    open fun solvePartTwo(input: List<String>): Int = throw NotImplementedError()

    open fun solvePartTwo(input: String): Int = solvePartTwo(input.lines())

    fun solvePartTwo(input: Input): Int = solvePartTwo(readInput(year, day, input))

    fun solve() {
        solvePartOne()
        solvePartTwo()
    }

    fun solvePartOne() {
        println("Running part one of day $day in year ${year}.")
        println("Year $year, day $day, part one: ${solvePartOneString(Input.Real)}")
    }

    fun solvePartTwo()  {
        println("Running part two of day $day in year ${year}.")
        println("Year $year, day $day, part two: ${solvePartTwoString(Input.Real)}")
    }

    fun runPartOneTest() {
        println("Testing part one of day $day in year ${year}.")
        println("TEST - Year $year, day $day, part one: ${solvePartOneString(Input.Test)}")
    }

    fun runPartTwoTest() {
        println("Testing part two of day $day in year ${year}.")
        println("TEST - Year $year, day $day, part two: ${solvePartTwoString(Input.Test)}")
    }
}
