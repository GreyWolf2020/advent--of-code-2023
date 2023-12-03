fun main() {
    fun part1(input: List<String>): Int {
        val maxGame = Game(
            0,
            maxBlue = 14,
            maxRed = 12,
            maxGreen = 13
        )
        val sumOfPossibleGames = input.map { gameData ->
            val (game, gameInput) = gameData.split(": ")
            val (_, gameNum) = game.split(" ")
            Game(num = gameNum.toInt())
                .apply {
                    val gameSets = gameInput
                        .split("; ", ", ")
                        .forEach { cube ->
                            val (cubeNum, cubeColor) = cube.split(" ")
                            this.isCubeGreater(Pair(cubeColor, cubeNum.toInt()))
                        }
                }
        }.filter { game ->
            game.maxBlue <= maxGame.maxBlue && game.maxRed <= maxGame.maxRed && game.maxGreen <= maxGame.maxGreen
        }.sumOf { it.num }
        return sumOfPossibleGames
    }

    fun part2(input: List<String>): Int = input
        .map { gameData ->
        val (game, gameInput) = gameData.split(": ")
        val (_, gameNum) = game.split(" ")
        Game(num = gameNum.toInt())
            .apply {
                val gameSets = gameInput
                    .split("; ", ", ")
                    .forEach { cube ->
                        val (cubeNum, cubeColor) = cube.split(" ")
                        this.isCubeGreater(Pair(cubeColor, cubeNum.toInt()))
                    }
            }
    }.sumOf { it.maxBlue * it.maxRed * it.maxGreen }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

data class Game(
    val num: Int,
    var isPossible: Boolean = true,
    var maxBlue: Int = 0,
    var maxGreen: Int = 0,
    var maxRed: Int = 0
) {
    fun isCubeGreater(cube: Pair<String, Int>) {
        when(cube.first) {
            "blue" -> {
                maxBlue = maxOf(maxBlue, cube.second)
            }
            "red" -> {
                maxRed = maxOf(maxRed, cube.second)
            }
            "green" -> {
                maxGreen = maxOf(maxGreen, cube.second)
            }
            else -> {
                println("There is an error in parsing the cubes")
            }
        }
    }
}