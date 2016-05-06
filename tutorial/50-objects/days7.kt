
val monthLength = intArrayOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
val weekday = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday",
		      "Friday", "Saturday", "Sunday")
val monthname = arrayOf("January", "February", "March",
		        "April", "May", "June",
		        "July", "August", "September",
		        "October", "November", "December")

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

  fun num2date(n: Int): Date {
    val fourY = (365 + 365 + 365 + 366)
    var year = 1901 + (n / fourY) * 4
    var day = n % fourY 
    if (day >= 365 + 365 + 365 + 59) {
      year += 3
      day -= 365 + 365 + 365
    } else {
      year += (day / 365)
      day = day % 365
      if (day >= 59)
	day += 1
    }
    var month = 1
    while (day >= monthLength[month-1]) {
      day -= monthLength[month-1]
      month += 1
    }
    return Date(year, month, day+1)
  }

  fun dayOfWeek(): String = weekday[(dayIndex() + 1) % 7]

  override fun toString(): String = 
    "%s, %s %d, %d".format(dayOfWeek(), monthname[month-1],
			   day, year)

  operator fun minus(rhs: Date): Int = dayIndex() - rhs.dayIndex()

  operator fun plus(n: Int): Date = num2date(dayIndex() + n)
  operator fun minus(n: Int): Date = num2date(dayIndex() - n)

}
