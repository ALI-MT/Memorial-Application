package ir.alimatin.memorial.utilities.calendar

import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

object ChangeDate {
    val currentDate: String
        get() {
            val curDate = FDate(System.currentTimeMillis())
            return curDate.toString()
        }

    //    String time = curDate.getHour() + ":"+curDate.getMinute()+":"+curDate.getSecond();
    val currentTime: String
        get() {
            val curDate = FDate(System.currentTimeMillis())
            //    String time = curDate.getHour() + ":"+curDate.getMinute()+":"+curDate.getSecond();
            return getCompleteTimeString_persiancoders(curDate)
        }

    fun getCompleteTimeString_persiancoders(fdate: FDate): String {
        val b = StringBuffer()
        b.append(if (fdate.hour < 10) "0" + fdate.hour else fdate.hour.toString())
        b.append(":")
        b.append(if (fdate.minute < 10) "0" + fdate.minute else fdate.minute.toString())
        b.append(":")
        b.append(if (fdate.second < 10) "0" + fdate.second else fdate.second.toString())
        return b.toString()
    }

    val currentYear: Int
        get() {
            val curDate = FDate(System.currentTimeMillis())
            return curDate.year
        }
    val currentMonth: Int
        get() {
            val curDate = FDate(System.currentTimeMillis())
            return curDate.month
        }
    val currentDay: Int
        get() {
            val curDate = FDate(System.currentTimeMillis())
            return curDate.date
        }

    fun toDate(formattedDate: String): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
        val pos = ParsePosition(0)
        return dateFormat.parse(formattedDate, pos)!!
    }

    fun invertDate(fdate: String?): String {
        var yyyy: String? = null
        var mm: String? = null
        var dd: String? = null
        if (fdate == null || fdate.length == 0) return ""
        val strTokenizer = StringTokenizer(fdate, "/")
        if (strTokenizer.hasMoreTokens()) {
            yyyy = strTokenizer.nextToken()
            if (strTokenizer.hasMoreTokens()) {
                mm = strTokenizer.nextToken()
                if (strTokenizer.hasMoreTokens()) {
                    dd = strTokenizer.nextToken()
                    return "$dd/$mm/$yyyy"
                }
            }
        }
        return fdate
    }

    fun changeFarsiToMiladi(farsiDate: String): String {
        val miladiDate = ShamsiCalendar.shamsiToMiladi_persiancoders(farsiDate)
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale("en"))
        return dateFormat.format(miladiDate)
    }

    fun changeMiladiToFarsi(miladiDate: String, time: List<String>): String {
        if (time[0].toInt() >= 19 && time[1].toInt() >= 30)
            return ShamsiCalendar.nextDay(
                ShamsiCalendar.nextDay(
                    ShamsiCalendar.miladiToShamsi_persiancoders_com(
                        toDate(
                            miladiDate
                        )
                    )
                )
            )
        else
            return ShamsiCalendar.nextDay(
                ShamsiCalendar.miladiToShamsi_persiancoders_com(
                    toDate(
                        miladiDate
                    )
                )
            )
    }

    /**  ------- ex-> 04:55   -----------  */
    fun convertToTehranTime(time: String): String {
        val df = SimpleDateFormat("HH:mm")
        df.timeZone = TimeZone.getTimeZone("UTC")
        var date: Date? = null
        try {
            date = df.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        df.timeZone = TimeZone.getTimeZone("GMT+4:30")

        return df.format(date!!)
    }

    val weekDayName: String
        get() = ShamsiCalendar.weekDayName(ShamsiCalendar.dayOfWeek(currentDate))
    val dayMounthYear: String
        get() = ShamsiCalendar.weekDayName(ShamsiCalendar.dayOfWeek(currentDate)) + " " +
                ShamsiCalendar.monthDayName(currentDay) +
                ShamsiCalendar.monthName(currentMonth) + " ماه " + currentYear.toString()

    fun decreaseYear(CurrDate: String, cnt: Int): String {
        val year = CurrDate.substring(0, 4)
        val ny = Integer.decode(year) - cnt
        return ny.toString() + CurrDate.substring(4)
    }

    fun decreaseCurrentYear(cnt: Int): String {
        val cur = currentDate
        val year = cur.substring(0, 4)
        val ny = Integer.decode(year) - cnt
        return ny.toString() + cur.substring(4)
    }

    fun increaseYear(tavalodDate: String, cnt: Int): String {
        val year = tavalodDate.substring(0, 4)
        val ny = Integer.decode(year) + cnt
        return ny.toString() + tavalodDate.substring(4)
    }

    fun increaseCurrentYear(cnt: Int): String {
        val cur = currentDate
        val year = cur.substring(0, 4)
        val ny = Integer.decode(year) + cnt
        return ny.toString() + cur.substring(4)
    }

    val currentDateTimeString: String
        get() {
            val curDate = FDate(System.currentTimeMillis())
            return curDate.toString() + " " + getCompleteTimeString_persiancoders(curDate)
        }
}