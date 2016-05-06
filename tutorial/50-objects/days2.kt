
val monthLength = intArrayOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

data class Date(val year: Int, val month: Int, val day: Int) {
  init {
    require(1901 <= year && year <= 2099)
    require(1 <= month && month <= 12)
    require(1 <= day && day <= monthLength[month - 1])
    require(month != 2 || day <= 28 || (year % 4) == 0)
  }

  // returns the number of days since 1901/01/01 (day 0)
  fun dayIndex(): Int {
    val fourY = (365 + 365 + 365 + 366)
    val yearn = year - 1901
    var total = 0
    total += fourY * (yearn / 4)
    total += 365 * (yearn % 4)
    for (m in 0 until month - 1)
      total += monthLength[m]
    total += day - 1
    if (year%4 != 0 && month > 2)
      total -= 1
    return total
  }
}
