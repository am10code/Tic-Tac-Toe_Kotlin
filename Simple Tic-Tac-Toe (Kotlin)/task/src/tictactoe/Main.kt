package tictactoe

class TicTacToe {
    var field = mutableListOf(
        mutableListOf(' ', ' ', ' '),
        mutableListOf(' ', ' ', ' '),
        mutableListOf(' ', ' ', ' ')
    )

    fun printField() {
        println("---------")
        for (i in 0..2) {
            print("| ")
            for (j in 0..2) {
                if (j != 2) {
                    print(field[i][j] + " ")
                } else {
                    print(field[i][j])
                }
            }
            print(" |\n")
        }
        println("---------")
    }

    fun updateField(string: String) {
        for (letterIndex in 1..string.length) {
            val line = (letterIndex - 1) / 3
            val lineElement = letterIndex - (3 * line + 1)
            if (string[letterIndex - 1] != '_') {
                field[line][lineElement] = string[letterIndex - 1]
            } else {
                field[line][lineElement] = ' '
            }

        }
    }

    fun updateOne(row: Int, column: Int, symbol: Char) {
        field[row][column] = symbol
    }

    fun moveCorrect(input: String, player: Char): Boolean {
        val coordinates = coordinatesChanger(input)
        if (!isCoordinatesCorrect(coordinates)) {
            println("Coordinates should be from 1 to 3!")
            return false
        }
        if (isCellBusy(coordinates)) {
            println("This cell is occupied! Choose another one!")
            return false
        }

        if (!isCellBusy(coordinates)) {
            updateOne((coordinates[0]-1),(coordinates[1]-1), player)
            return true
        }
        return true
    }

    private fun coordinatesChanger(input: String): MutableList<Int> {
        val coordinatesString = input.split(" ")
        var coordinates = mutableListOf<Int>()
        coordinates.add(coordinatesString[0].toInt())
        coordinates.add(coordinatesString[1].toInt())
        return coordinates
    }

    private fun isCellBusy(coordinates: MutableList<Int>): Boolean {
        return field[coordinates[0]-1][coordinates[1]-1] == 'X' ||
                field[coordinates[0]-1][coordinates[1]-1] == 'O'
    }

    private fun isCoordinatesCorrect(coordinates: MutableList<Int>): Boolean {
        return coordinates[0] in 1..3 && coordinates[1] in 1..3
    }


}

class AnalyzeState {
    fun check(string: MutableList<MutableList<Char>>): String {
        //if (impossibleQuantity(string)) return "Impossible"
        val list = string
        val xWins = winCounter('X', list)
        val oWins = winCounter('O', list)
        val emptyLinesExist = emptyFieldsChecker(list.joinToString(""))
        if (xWins == 0 && oWins == 0 && emptyLinesExist) return "Game not finished"
        if (xWins == 0 && oWins == 0 && !emptyLinesExist) return "Draw"
        //if (xWins > 0 && oWins > 0) return "Impossible"
        if (xWins > oWins) return "X wins" else return "O wins"
    }

    private fun impossibleQuantity(string: String): Boolean {
        var symbolX = 0
        var symbolO = 0
        for (char in string) {
            if (char == 'X') {
                symbolX++
            } else if (char == 'O') {
                symbolO ++
            }
        }
        val diff = symbolO - symbolX
        return kotlin.math.abs(diff) > 1
    }

    private fun winCounter(symbol: Char, list: MutableList<MutableList<Char>>): Int {
        var counter = 0
        counter += rowWins(symbol, list)
        counter += columnWins(symbol, list)
        counter += diagonalWins(symbol, list)
        return counter
    }

    private fun rowWins(symbol: Char, list: MutableList<MutableList<Char>>): Int {
        var wins = 0
        for (i in 0..2) {
            if (list[i][0] == list[i][1] && list[i][1] == list[i][2] && list[i][0] == symbol) wins++
        }
        return wins
    }

    private fun columnWins(symbol: Char, list: MutableList<MutableList<Char>>): Int {
        var wins = 0
        for (i in 0..2) {
            if (list[0][i] == list[1][i] && list[1][i] == list[2][i] && list[0][i] == symbol) wins++
        }
        return wins
    }

    private fun diagonalWins(symbol: Char, list: MutableList<MutableList<Char>>): Int {
        var wins = 0
        val slash = list[0][0] == list[1][1] && list[1][1] == list[2][2] && list[1][1] == symbol
        val backslash = list[2][0] == list[1][1] && list[1][1] == list[0][2] && list[1][1] == symbol
        if (slash) wins++
        if (backslash) wins++
        return wins
    }

    private fun emptyFieldsChecker(string: String): Boolean {
        for (char in string) {
            if (char == ' ') return true
        }
        return false
    }


    private fun stringToList(string: String): MutableList<MutableList<Char>> {
        return mutableListOf(
            mutableListOf(string[0], string[1], string[2]),
            mutableListOf(string[3], string[4], string[5]),
            mutableListOf(string[6], string[7], string[8])
        )
    }

}
fun main() {
    val game = TicTacToe()
    val result = AnalyzeState()
    game.printField()
    var player = 'X'
    while (true) {
        var move = readln()
        if (!game.moveCorrect(move, player)) {
            continue
        } else {
            game.printField()
            player = if (player == 'X') 'O' else 'X'
        }
        var total = result.check(game.field)
        when (total) {
            "O wins" -> {
                println(total)
                break
            }
            "X wins" -> {
                println(total)
                break
            }
            "Draw" -> {
                println(total)
                break
            }
            else -> continue
        }
    }

}