package kotlinTasks.footballChamp

import java.io.File
import java.io.IOException

/**
 * Билет 4 -- футбольный чемпионат (таблица)
 *
 * В файле с именем inputName заданы результаты игр
 * футбольного чемпионата в виде таблицы в следующем формате:
 *
 * Арсенал     0  4  1  1
 * Бавария     1  0  2  3
 * Интер       1  2  0  0
 * Барселона   3  8  0  0
 *
 * Элемент таблицы чисел из 1-й строки и j-го столбца содержит
 * число голов, забитое командой i в ворота команды j.
 * Например (см. выделенные элементы) команда 2 (Бавария) и
 * команда 4 (Барселона) сыграли со счётом 3:8.
 * Диагональные элементы (i=j) всегда равны 0.
 * Названия команд не могут содержать пробелы
 *
 * За победу в матче (большее количество мячей) команде даётся 3
 * очка, за ничью (одинаковое количество мячей) - 1 очко.
 *
 * Необходимо посчитать статистику каждой команды по итогам
 * чемпионата и вернуть её как результат функции.
 *
 * "Удовлетворительно" -- достаточно рассчитать суммарное
 * количество мячей, забитых и пропущеных каждой командой
 *
 * "Хорошо" -- также необходимо определить занятые командами
 * места по количеству набранных ими очков, а при равенстве очков
 * -- по разности забитых и пропущеных мячей.
 *
 * "Отлично" -- также необходимо определить занятые командами
 * места по количеству набранных ими очков, а при равенстве очков
 * -- по разности забитых и пропущеных мячей.
 *
 * При нарушении форматов входных данных следует выбрасывать
 * исключение IllegalArgumentException, при невозможности
 * прочитать файл выбрасывать исключение IOException.
 *
 * Предложить самостоятельно имя функции. Кроме функции следует
 * написать тесты, подтверждающие её работоспособность.
 */

// ручная отладка функции
fun main(args: Array<String>) {
    val inputName = "input/football.txt"
    leaderboard(inputName)
}

data class Team(val id: Int, val name: String, var games: MutableList<Game>) {

    // вычисление очков команды по победам
    fun points(): Int {
        var res = 0
        for (game in games) if (game.status() == 1) res += 3 else if (game.status() == 2) res += 1
        return res
    }

    // вычисление очков команды по разности забитых и пропущеных голов
    fun pointsByGoals(): Int {
        var res = 0
        for (game in games) res += (game.score.first - game.score.second)
        return res
    }

    // определение победителя среди двух команд
    fun winner(rival: Team): Boolean {
        if (rival.points() == points()) if (pointsByGoals() > rival.pointsByGoals()) return true
        return rival.points() < points()
    }

}

data class Game(val enemyId: Int, var score: Pair<Int, Int>) {
    // возвращает результат игры, где:  0 - проигрыш,   1 - выигрыш,   2 - ничья
    fun status(): Int = if (score.first > score.second) 1 else if (score.first == score.second) 2 else 0
}

fun leaderboard(inputName: String): List<Pair<Int, Team>> {
    val result = mutableListOf<Pair<Int, Team>>()
    val teamsList = mutableListOf<Team>()
    var count = 1
    // читаем данные с файла inputName в список, а так же
    // выбрасываем IOException при невозможности его прочтения
    val str = try { File(inputName).readLines() } catch (e: IOException)
    { throw IOException("Невозможно прочитать файл $inputName !") }
    for (item in str) {
        // проверяем каждую строку на соответсвтие формата
        // при несоответствии выбрасываем IllegalArgumentException
        if (!item.matches(Regex("""[a-zA-Zа-яА-Я]+\s+\d(\s+\d+)+""")))
            throw IllegalArgumentException("Файл $inputName не соответствует формату!")
        // при успешной проверке на соблюдение формата,
        // заменяем все пробелы между элементами на один пробел для удобной работы со строкой
        // потом разбиваем строку на элементы, разделенные одним пробелом

        // Бавария 1 0 2 3

        val line = item.replace(Regex("""(\s{2,})"""), " ").split(" ")
        // создаём список комманд с пока пустой историей их игр
        teamsList.add(Team(count, line.first(), mutableListOf()))
        count++
    }

    // создание массива на основе таблицы очков
    val scoreTab = Array(str.size) { Array(str.size) {0} }
    count = 0
    for (line in str.map { its ->
        // удаляем со строки все символы кроме чисел таблицы
        its.replace(Regex("""[a-zA-Zа-яА-Я]+\s+"""),
                "").split("  ").map { it.toInt() } }) {
        for (i in 0 until line.size) scoreTab[count][i] = line[i]
        count++
    }
    // заносим данные о прошедших играх для команды.
    // исходя из описания задачи, у нас
    // в качестве обозначения номера строки используется count
    // в качестве обозначения номера столбца используется i
    count = 0
    for (team in teamsList) {
        for (i in 0 until scoreTab[count].size) if (count != i)
            team.games.add(Game(i + 1, Pair(scoreTab[count][i], scoreTab[i][count])))
        count++
    }

    // определяем место команды и заносим в список
    for (item in teamsList) {
        var place = 0
        teamsList.forEach { if (item.winner(it) && (item != it)) place++ }
        place = teamsList.size - place
        result.add(Pair(place, item))
    }

    // ручная отладка (Вывод всех данных функции в консоль)
    printDb(scoreTab, teamsList, inputName)

    // Вывод строкового ответа в консоль
    //printResult(result.sortedBy { it.first })

    return result.sortedBy { it.first }
}

// Вывод ответа в консоль (Не обязательно по заданию, но по смыслу будет не лишним)
fun printResult(result: List<Pair<Int, Team>>) {
    println("Статистика комманд за проведенные игры:")
    for (item in result) println(item.first.toString() + " место: " + item.second.name +
            " (Очков по победам: " + item.second.points() +
            ", Очков по голам: " + item.second.pointsByGoals() + ")")
}

// Ручная отладка (!)ВСЕХ данных
// (Вывод всех обработанных данных в консоль)
// Я не уверен, что она вообще нужна по заданию,
// да и врядли вас попросят её реализовать.
// Осталась она тут только потому что
fun printDb(teamTab: Array<Array<Int>>, teamList: List<Team>, inputName: String) {
    var res = ""
    fun getById(id: Int): Team {
        teamList.forEach { if (it.id == id) return it }
        return Team(0, "null", mutableListOf())
    }

    println("Полученые данные с файла \"$inputName\" :\n")
    File(inputName).readLines().forEach { println(it) }
    println("\nВоссозданный нашей функцией массив: \n")
    for (item in teamTab) {
        item.forEach { print("  $it") }
        println("")
    }
    println("\nПолученный список команд:")
    for (item in teamList) {
        println("\n Id команды: " + item.id + ", Название команды: " + item.name)
        println("\tКоличество очков: " + item.points() +
                "\n\tКоличество очков по разности забитых и пропущеных голов: " + item.pointsByGoals() +
                "\n\t\tИстория игр команды:")
        for (game in item.games) {
            when {
                game.status() == 0 -> res = " - Проигрыш"
                game.status() == 1 -> res = " - Победа"
                game.status() == 2 -> res = " - Ничья"
            }
            println("\t\t " + item.name + " - " + getById(game.enemyId).name + "" + " - " + game.score.first +
                    ":" + game.score.second + "\tРезультат игры: " + game.status() + res)
        }
    }
}