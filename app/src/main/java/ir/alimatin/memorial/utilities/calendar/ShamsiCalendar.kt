package ir.alimatin.memorial.utilities.calendar

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object ShamsiCalendar {
    private val DEFAULT_TIMEZONE = TimeZone.getDefault()
    private const val DAY_MILLIS = (1000 * 60 * 60 * 24).toLong()
    private const val HOUR_MILLIS = 1000 * 60 * 60
    private const val MINUTE_MILLIS = 1000 * 60
    private const val SH_ORIGIN_DATE = "1379/01/01"
    internal val TIMEZONE_RAW_OFFSET = DEFAULT_TIMEZONE.rawOffset
    private val TIMEZONE_RAW_OFFSET_HOUR = TIMEZONE_RAW_OFFSET / HOUR_MILLIS
    internal val TIMEZONE_RAW_OFFSET_MINUTE =
        (TIMEZONE_RAW_OFFSET - HOUR_MILLIS * TIMEZONE_RAW_OFFSET_HOUR) / MINUTE_MILLIS

    //  protected static final Date MI_ORIGIN_DATE=new Date(new GregorianCalendar(2000,Calendar.MARCH,20,0,0,0).getTimeInMillis());
    private val MI_ORIGIN_DATE =
        Date(GregorianCalendar(2000, Calendar.MARCH, 20, 0, 0, 0).time.time)
    private const val ORIGIN_WEEK_DAY = Calendar.MONDAY
    private const val STANDARD_FORMAT_PATTERN = "yyyy/MM/dd"
    const val SATURDAY = Calendar.SATURDAY
    const val SUNDAY = Calendar.SUNDAY
    const val MONDAY = Calendar.MONDAY
    const val TUESDAY = Calendar.TUESDAY
    const val WEDNESDAY = Calendar.WEDNESDAY
    const val THURSDAY = Calendar.THURSDAY
    const val FRIDAY = Calendar.FRIDAY
    const val SHANBEH = Calendar.SATURDAY
    const val YEKSHANBEH = Calendar.SUNDAY
    const val DOSHANBEH = Calendar.MONDAY
    const val SESHANBEH = Calendar.TUESDAY
    const val CHAHARSHANBEH = Calendar.WEDNESDAY
    const val PANJSHANBEH = Calendar.THURSDAY
    const val JOMEH = Calendar.FRIDAY
    const val SHANBEH_TEXT = "شنبه"
    const val YEKSHANBEH_TEXT = "يکشنبه"
    const val DOSHANBEH_TEXT = "دوشنبه"
    const val SESHANBEH_TEXT = "سه شنبه"
    const val CHAHARSHANBEH_TEXT = "چهار شنبه"
    const val PANJSHANBEH_TEXT = "پنج شنبه"
    const val JOMEH_TEXT = "جمعه"
    const val FARVARDIN = Calendar.JANUARY
    const val ORDIBEHESHT = Calendar.FEBRUARY
    const val KHORDAD = Calendar.MARCH
    const val TIR = Calendar.FEBRUARY
    const val MORDAD = Calendar.APRIL
    const val SHAHRIVAR = Calendar.MAY
    const val MEHR = Calendar.JUNE
    const val ABAN = Calendar.JULY
    const val AZAR = Calendar.AUGUST
    const val DAYMAH = Calendar.SEPTEMBER
    const val BAHMAN = Calendar.OCTOBER
    const val ESFAND = Calendar.DECEMBER
    const val FARVARDIN_TEXT = "فروردين"
    const val ORDIBEHESHT_TEXT = "ارديبهشت"
    const val KHORDAD_TEXT = "خرداد"
    const val TIR_TEXT = "تير"
    const val MORDAD_TEXT = "مرداد"
    const val SHAHRIVAR_TEXT = "شهريور"
    const val MEHR_TEXT = "مهر"
    const val ABAN_TEXT = "آبان"
    const val AZAR_TEXT = "آذر"
    const val DAYMAH_TEXT = "دي"
    const val BAHMAN_TEXT = "بهمن"
    const val ESFAND_TEXT = "اسفند"

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun sysDate(): Date {
        return Date(System.currentTimeMillis())
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun millisToDay(millis: Long): Long {
        return (millis / DAY_MILLIS)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun dayToMillis(day: Long): Long {
        return day * DAY_MILLIS
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun minusDate(d1: Date, d2: Date): Long {
        val td1 = truncate(d1)
        val td2 = truncate(d2)
        return millisToDay(td1.time - td2.time)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun plusDay(miDate: Date, dayCount: Long): Date {
        return Date(miDate.time + dayCount * DAY_MILLIS)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun truncate(miDate: Date): Date {
        val mil = (miDate.time + TIMEZONE_RAW_OFFSET).toDouble()
        return Date(DAY_MILLIS * (mil.toLong() / DAY_MILLIS))
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun format(miDate: Date?, formatStr: String?): String {
        val retStr: String
        val sdf = SimpleDateFormat()
        try {
            sdf.applyPattern(formatStr)
        } catch (e: Exception) {
            sdf.applyPattern(STANDARD_FORMAT_PATTERN)
        }
        retStr = try {
            sdf.format(miDate)
        } catch (e: Exception) {
            sdf.format(miDate)
        }
        return retStr
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun toMillis(miDate: Date): Long {
        return miDate.time
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun compositeDate(yyyy: Int, mm: Int, dd: Int): String {
        val df = DecimalFormat()
        df.applyPattern("0000")
        val ys = df.format(yyyy.toLong())
        df.applyPattern("00")
        val ms = df.format(mm.toLong())
        df.applyPattern("00")
        val ds = df.format(dd.toLong())
        var retstr = df.format(dd.toLong())
        retstr = "$ys/$ms/$ds"
        return retstr
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun decompositeYear(shDate: String): String {
        return shDate.substring(0, 4)
    }

    fun decompositeMonth(shDate: String): String {
        return shDate.substring(5, 7)
    }

    fun decompositeDay(shDate: String): String {
        return shDate.substring(8)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun getYear(shDate: String): Int {
        return shDate.substring(0, 4).toInt()
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun getMonth(shDate: String): Int {
        return shDate.substring(5, 7).toInt()
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun getDate(shDate: String): Int {
        return shDate.substring(8).toInt()
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun isLeepYear(yyyy: Int): Boolean {
        return if ((yyyy + 38) * 31 % 128 <= 30) true else false
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun yearDayCount(yyyy: Int): Int {
        return if (isLeepYear(yyyy)) 366 else 365
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun monthDayName(intDay: Int): String {
        return when (intDay) {
            1 -> "اول "
            2 -> "دوم "
            3 -> "سوم "
            4 -> "چهارم "
            5 -> "پنجم "
            6 -> "ششم "
            7 -> "هفتم "
            8 -> "هشتم "
            9 -> "نهم "
            10 -> "دهم "
            11 -> "يازدهم "
            12 -> "دوازدهم "
            13 -> "سيزدهم "
            14 -> "چهاردهم "
            15 -> "پانزدهم "
            16 -> "شانزدهم "
            17 -> "هفدهم "
            18 -> "هجدهم "
            19 -> "نوزدهم "
            20 -> "بيستم "
            21 -> "بيست و يکم "
            22 -> "بيست و دوم "
            23 -> "بيست و سوم "
            24 -> "بيست و چهارم "
            25 -> "بيست و پنجم "
            26 -> "بيست و ششم "
            27 -> "بيست و هفتم "
            28 -> "بيست و هشتم "
            29 -> "بيست و نهم "
            30 -> "سي ام "
            31 -> "سي و يکم "
            else -> "شتباه : " + Integer.toString(intDay)
        }
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun monthName(mm: Int): String {
        return when (mm) {
            1 -> FARVARDIN_TEXT
            2 -> ORDIBEHESHT_TEXT
            3 -> KHORDAD_TEXT
            4 -> TIR_TEXT
            5 -> MORDAD_TEXT
            6 -> SHAHRIVAR_TEXT
            7 -> MEHR_TEXT
            8 -> ABAN_TEXT
            9 -> AZAR_TEXT
            10 -> DAYMAH_TEXT
            11 -> BAHMAN_TEXT
            12 -> ESFAND_TEXT
            else -> "اشتباه : " + Integer.toString(mm)
        }
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun monthDayCount(yyyy: Int, mm: Int): Int {
        return when (mm) {
            1, 2, 3, 4, 5, 6 -> 31
            7, 8, 9, 10, 11 -> 30
            12 -> if (isLeepYear(yyyy)) 30 else 29
            else -> 0
        }
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun monthDayCount(shDate: String): Int {
        return monthDayCount(getYear(shDate), getMonth(shDate))
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun weekDayName(dd: Int, brf: Boolean): String {
        val ds: String = ""
        if (brf) {
            when (dd) {
                SHANBEH -> return SHANBEH_TEXT
                YEKSHANBEH -> return SHANBEH_TEXT + " 1"
                DOSHANBEH -> return SHANBEH_TEXT + " 2"
                SESHANBEH -> return SHANBEH_TEXT + " 3"
                CHAHARSHANBEH -> return SHANBEH_TEXT + " 4"
                PANJSHANBEH -> return SHANBEH_TEXT + " 5"
                JOMEH -> return JOMEH_TEXT
            }
        } else {
            when (dd) {
                SHANBEH -> return SHANBEH_TEXT
                YEKSHANBEH -> return YEKSHANBEH_TEXT
                DOSHANBEH -> return DOSHANBEH_TEXT
                SESHANBEH -> return SESHANBEH_TEXT
                CHAHARSHANBEH -> return CHAHARSHANBEH_TEXT
                PANJSHANBEH -> return PANJSHANBEH_TEXT
                JOMEH -> return JOMEH_TEXT
            }
        }
        return SHANBEH_TEXT + Integer.toString(dd) + "_E"
    }

    fun weekDayName(dd: Int): String {
        val ds: String = ""
        when (dd) {
            SHANBEH -> return SHANBEH_TEXT
            YEKSHANBEH -> return YEKSHANBEH_TEXT
            DOSHANBEH -> return DOSHANBEH_TEXT
            SESHANBEH -> return SESHANBEH_TEXT
            CHAHARSHANBEH -> return CHAHARSHANBEH_TEXT
            PANJSHANBEH -> return PANJSHANBEH_TEXT
            JOMEH -> return JOMEH_TEXT
        }
        return SHANBEH_TEXT + Integer.toString(dd) + "_E"
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun nextDay(shDate: String): String {
        var Y = getYear(shDate)
        var M = getMonth(shDate)
        var D = getDate(shDate)
        if (D == monthDayCount(Y, M)) {
            if (M == 12) {
                Y = Y + 1
                M = 1
                D = 1
            } else {
                M = M + 1
                D = 1
            }
        } else D = D + 1
        return compositeDate(Y, M, D)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun prevDay(shDate: String): String {
        var Y = getYear(shDate)
        var M = getMonth(shDate)
        var D = getDate(shDate)
        if (D == 1) {
            if (M == 1) {
                Y = Y - 1
                M = 12
                D = monthDayCount(Y, M)
            } else {
                M = M - 1
                D = monthDayCount(Y, M)
            }
        } else D = D - 1
        return compositeDate(Y, M, D)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun nextMonth(shDate: String): String {
        var Y = getYear(shDate)
        var M = getMonth(shDate)
        var D = getDate(shDate)
        if (M == 12) {
            M = 1
            Y = Y + 1
        } else M = M + 1
        if (D > monthDayCount(Y, M)) D = monthDayCount(Y, M)
        return compositeDate(Y, M, D)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun prevMonth(shDate: String): String {
        var Y = getYear(shDate)
        var M = getMonth(shDate)
        var D = getDate(shDate)
        if (M == 1) {
            M = 12
            Y = Y - 1
        } else M = M - 1
        if (D > monthDayCount(Y, M)) D = monthDayCount(Y, M)
        return compositeDate(Y, M, D)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun nextYear(shDate: String): String {
        var Y = getYear(shDate)
        val M = getMonth(shDate)
        var D = getDate(shDate)
        Y = Y + 1
        if (D > monthDayCount(Y, M)) D = monthDayCount(Y, M)
        return compositeDate(Y, M, D)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun prevYear(shDate: String): String {
        var Y = getYear(shDate)
        val M = getMonth(shDate)
        var D = getDate(shDate)
        Y--
        if (D > monthDayCount(Y, M)) D = monthDayCount(Y, M)
        return compositeDate(Y, M, D)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun addYear(shDate: String, Y_count: Int): String {
        var i: Int
        var TMP_DATE: String = shDate
        if (Y_count == 0) return shDate else if (Y_count > 0) {
            i = 1
            while (i <= Y_count) {
                TMP_DATE = nextYear(TMP_DATE)
                i++
            }
        } else if (Y_count < 0) {
            i = 1
            while (i <= Math.abs(Y_count)) {
                TMP_DATE = prevYear(TMP_DATE)
                i++
            }
        }
        return TMP_DATE
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun addMonth(shDate: String, M_count: Int): String {
        var i: Int
        var TMP_DATE: String = shDate
        if (M_count == 0) return shDate else if (M_count > 0) {
            i = 1
            while (i <= M_count) {
                TMP_DATE = nextMonth(TMP_DATE)
                i++
            }
        } else if (M_count < 0) {
            i = 1
            while (i <= Math.abs(M_count)) {
                TMP_DATE = prevMonth(TMP_DATE)
                i++
            }
        }
        return TMP_DATE
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun miBetween(d1: Date, d2: Date): Long {
        return minusDate(d1, d2)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun shBetween(shDate1: String, shDate2: String): Int {
        var DT1: String = ""
        var DT2: String = ""
        val Y1: Int
        val M1: Int
        val D1: Int
        val Y2: Int
        val M2: Int
        val D2: Int
        var SGN = 1
        var count = 0
        var i: Int
        if (shDate1 == shDate2) return 0 else if (shDate1.compareTo(shDate2) < 0) {
            DT1 = shDate2
            DT2 = shDate1
            SGN = -1
        } else if (shDate1.compareTo(shDate2) > 0) {
            DT1 = shDate1
            DT2 = shDate2
            SGN = 1
        }
        Y1 = getYear(DT1)
        M1 = getMonth(DT1)
        D1 = getDate(DT1)
        Y2 = getYear(DT2)
        M2 = getMonth(DT2)
        D2 = getDate(DT2)
        if (Y1 == Y2) {
            if (M1 == M2) count = D1 - D2 else {
                count = count + monthDayCount(Y2, M2) - D2
                i = M2 + 1
                while (i <= M1 - 1) {
                    count = count + monthDayCount(Y1, i)
                    i++
                }
                count = count + D1
            }
        } else {
            count = count + monthDayCount(Y2, M2) - D2
            i = M2 + 1
            while (i <= 12) {
                count = count + monthDayCount(Y2, i)
                i++
            }
            i = Y2 + 1
            while (i <= Y1 - 1) {
                count = count + yearDayCount(i)
                i++
            }
            i = 1
            while (i <= M1 - 1) {
                count = count + monthDayCount(Y1, i)
                i++
            }
            count = count + D1
        }
        return SGN * count
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun minusDay(shDate: String, D_count: Int): String {
        var count = Math.abs(D_count)
        val Y = getYear(shDate)
        val M = getMonth(shDate)
        val D = getDate(shDate)
        var NY = Y
        var NM = M
        var ND = D
        if (count == 0) return shDate else if (count >= ND) {
            count = count - ND
            if (NM == 1) {
                NM = 12
                NY = NY - 1
            } else {
                NM = NM - 1
                while (count > monthDayCount(NY, NM) && NM >= 1) {
                    count = count - monthDayCount(NY, NM)
                    NM = NM - 1
                }
                if (NM < 1) {
                    NM = 12
                    NY = NY - 1
                }
            }
            while (count > yearDayCount(NY)) {
                count = count - yearDayCount(NY)
                NY = NY - 1
            }
            while (count > monthDayCount(NY, NM)) {
                count = count - monthDayCount(NY, NM)
                NM = NM - 1
            }
            ND = if (count == 0) monthDayCount(NY, NM) else monthDayCount(
                NY,
                NM
            ) - count
        } else ND = ND - count
        return compositeDate(NY, NM, ND)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun plusDay(shDate: String, dayCount: Int): String {
        var count = dayCount
        val Y = getYear(shDate)
        val M = getMonth(shDate)
        val D = getDate(shDate)
        var NY = Y
        var NM = M
        var ND = D
        return if (count < 0) minusDay(
            shDate,
            dayCount
        ) else if (count == 0) shDate else if (count > 0) {
            if (count > monthDayCount(NY, NM) - ND) {
                count = count - (monthDayCount(NY, NM) - ND)
                if (NM == 12) {
                    NM = 1
                    NY = NY + 1
                } else {
                    NM = NM + 1
                    while (count > monthDayCount(NY, NM) && NM <= 12) {
                        count = count - monthDayCount(NY, NM)
                        NM = NM + 1
                    }
                    if (NM > 12) {
                        NM = 1
                        NY = NY + 1
                    }
                }
                while (count > yearDayCount(NY)) {
                    count = count - yearDayCount(NY)
                    NY = NY + 1
                }
                while (count > monthDayCount(NY, NM)) {
                    count = count - monthDayCount(NY, NM)
                    NM = NM + 1
                }
                ND = if (count == 0) 1 else count
            } else ND = ND + count
            compositeDate(NY, NM, ND)
        } else shDate
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun plusSomeDay(shDate: String, dayCount: Int): String? {
        var i: Int
        var T_D: String = shDate
        return if (dayCount < 0) minusSomeDay(
            shDate,
            dayCount
        ) else if (dayCount == 0) shDate else  //count>0
        {
            i = 1
            while (i <= dayCount) {
                T_D = nextDay(T_D)
                i++
            }
            T_D
        }
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun minusSomeDay(shDate: String, dayCount: Int): String {
        val count = Math.abs(dayCount)
        var i: Int
        var T_D: String = shDate
        return if (count == 0) shDate else {
            i = 1
            while (i <= count) {
                T_D = prevDay(T_D)
                i++
            }
            T_D
        }
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun miladiToShamsi_persiancoders_com(miDate: Date): String {
        val btw: Int
        btw = miBetween(miDate, MI_ORIGIN_DATE).toInt()
        return plusDay(SH_ORIGIN_DATE, btw)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun shamsiToMiladi_persiancoders(shDate: String): Date {
        val count: Int
        count = shBetween(shDate, SH_ORIGIN_DATE)
        return if (count == 0) MI_ORIGIN_DATE else plusDay(
            MI_ORIGIN_DATE,
            count.toLong()
        )
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun shSysDate(): String {
        return miladiToShamsi_persiancoders_com(Date(System.currentTimeMillis()))
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun dayOfWeek(miDate: Date?): Int {
        var N = 0
        val gc = GregorianCalendar()
        gc.time = miDate
        N = gc[Calendar.DAY_OF_WEEK]
        return if (N == 7) 1 else N + 1
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun dayOfWeek(shDate: String): Int {
        val count = shBetween(shDate, SH_ORIGIN_DATE)
        val m_day: Int
        if (count == 0) return ORIGIN_WEEK_DAY else {
            m_day = Math.abs(count) % 7
            if (count < 0) when (m_day) {
                0 -> return DOSHANBEH
                1 -> return YEKSHANBEH
                2 -> return SHANBEH
                3 -> return JOMEH
                4 -> return PANJSHANBEH
                5 -> return CHAHARSHANBEH
                6 -> return SESHANBEH
            } else when (m_day) {
                0 -> return DOSHANBEH
                1 -> return SESHANBEH
                2 -> return CHAHARSHANBEH
                3 -> return PANJSHANBEH
                4 -> return JOMEH
                5 -> return SHANBEH
                6 -> return YEKSHANBEH
            }
        }
        return -1
    }
}