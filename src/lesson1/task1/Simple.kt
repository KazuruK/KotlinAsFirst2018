@file:Suppress("UNUSED_PARAMETER")

package lesson1.task1

import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление квадрата целого числа
 */
fun sqr(x: Int) = x * x

/**
 * Пример
 *
 * Вычисление квадрата вещественного числа
 */
fun sqr(x: Double) = x * x

/**
 * Пример
 *
 * Вычисление дискриминанта квадратного уравнения
 */
fun discriminant(a: Double, b: Double, c: Double) = sqr(b) - 4 * a * c

/**
 * Пример
 *
 * Поиск одного из корней квадратного уравнения
 */
fun quadraticEquationRoot(a: Double, b: Double, c: Double) =
        (-b + sqrt(discriminant(a, b, c))) / (2 * a)

/**
 * Пример
 *
 * Поиск произведения корней квадратного уравнения
 */
fun quadraticRootProduct(a: Double, b: Double, c: Double): Double {
    val sd = sqrt(discriminant(a, b, c))
    val x1 = (-b + sd) / (2 * a)
    val x2 = (-b - sd) / (2 * a)
    return x1 * x2 // Результат
}

/**
 * Пример главной функции
 */
fun main(args: Array<String>) {
    val x1x2 = quadraticRootProduct(1.0, 13.0, 42.0)
    println("Root product: $x1x2")
}

/**
 * Тривиальная
 *
 * Пользователь задает время в часах, минутах и секундах, например, 8:20:35.
 * Рассчитать время в секундах, прошедшее с начала суток (30035 в данном случае).
 */
fun seconds(hours: Int, minutes: Int, seconds: Int): Int {
    val h = hours * 3600
    val m = minutes * 60
    return h + m + seconds
}

/**
 * Тривиальная
 *
 * Пользователь задает длину отрезка в саженях, аршинах и вершках (например, 8 саженей 2 аршина 11 вершков).
 * Определить длину того же отрезка в метрах (в данном случае 18.98).
 * 1 сажень = 3 аршина = 48 вершков, 1 вершок = 4.445 см.
 */
fun lengthInMeters(sagenes: Int, arshins: Int, vershoks: Int): Double {
    val sagenesInVersoks = sagenes * 48
    val arshinsInVersoks = arshins * 16
    return (sagenesInVersoks + arshinsInVersoks + vershoks) * 4.445 / 100

}

/**
 * Тривиальная
 *
 * Пользователь задает угол в градусах, минутах и секундах (например, 36 градусов 14 минут 35 секунд).
 * Вывести значение того же угла в радианах (например, 0.63256).
 */
fun angleInRadian(grad: Int, min: Int, sec: Int): Double = TODO()

/**
 * Тривиальная
 *
 * Найти длину отрезка, соединяющего точки на плоскости с координатами (x1, y1) и (x2, y2).
 * Например, расстояние между (3, 0) и (0, 4) равно 5
 */
fun trackLength(x1: Double, y1: Double, x2: Double, y2: Double): Double = TODO()

/**
 * Простая
 *
 * Пользователь задает целое число, большее 100 (например, 3801).
 * Определить третью цифру справа в этом числе (в данном случае 8).
 */
fun thirdDigit(number: Int): Int {
    val third = number % 1000
    return third / 100
}

/**
 * Простая
 *
 * Поезд вышел со станции отправления в h1 часов m1 минут (например в 9:25) и
 * прибыл на станцию назначения в h2 часов m2 минут того же дня (например в 13:01).
 * Определите время поезда в пути в минутах (в данном случае 216).
 */
fun travelMinutes(hoursDepart: Int, minutesDepart: Int, hoursArrive: Int, minutesArrive: Int): Int {
    val h2InM2 = (hoursArrive * 60) + minutesArrive
    val h1InM1 = (hoursDepart * 60) + minutesDepart
    return h2InM2 - h1InM1
}

/**
 * Простая
 *
 * Человек положил в банк сумму в s рублей под p% годовых (проценты начисляются в конце года).
 * Сколько денег будет на счету через 3 года (с учётом сложных процентов)?
 * Например, 100 рублей под 10% годовых превратятся в 133.1 рубля
 */
fun accountInThreeYears(initial: Int, percent: Int): Double = TODO()

/**
 * Простая
 *
 * Пользователь задает целое трехзначное число (например, 478).
 * Необходимо вывести число, полученное из заданного перестановкой цифр в обратном порядке (например, 874).
 */
fun numberRevert(number: Int): Int {
    val thirdNumber = number % 10
    val secondNumber = (number / 10) % 10
    val firstNumber = number / 100
    return (thirdNumber * 100) + (secondNumber * 10) + firstNumber


/*
Номера телефонов
fun telephone(inputName: String, query: String): String {
val inputStream = File(inputName).readLines()
if (inputName.isEmpty())
throw IOException()
val result = StringBuilder()
var first = query.split(" ")
inputStream.forEach { lines ->
val newLine = lines.split("-")
if (newLine[0].trim() == first[0]) {
val splName = newLine[1].split(",")
for (i in 0 until splName.size) {
val newresult = splName[i].split(" ")
if (newresult[0].trim() == first[1])
result.append(newresult[1])
}
}
}
return result.toString()
}

*/

/* Аеропорты
fun plane(inputName: String, src: String, dst: String): Boolean {
val inputStream = File(inputName).readLines()
if (inputName.isEmpty())
throw IOException()
var first = false
var second = false
inputStream.forEach { lines ->
if (!Regex("""[a-zA-Z\d+]+\s[a-zA-Z]+\s>\s\d\d:\d\d""").matches(inputName) ||
!Regex("""[a-zA-Z\d+]+\s[a-zA-Z]+\s<\s\d\d:\d\d""").matches(inputName) ||
!Regex("""[a-zA-Z]+""").matches(src) ||
!Regex("""[a-zA-Z]+""").matches(dst))
throw IllegalArgumentException()
var word = lines.split(" ")
first = word[1] == src && word[2] == ">"
second = word[1] == dst && word[2] == "<"
}
return first && second
}
 */

//triadecom