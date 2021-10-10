package ir.alimatin.memorial.utilities.calendar

import java.text.DecimalFormat
import java.util.*

open class FDate {
    private lateinit var internalDate: Date
    protected lateinit var internalShamsiDate: String

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    constructor(millis: Long) {
        set(millis)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    constructor() {
        set()
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    constructor(year: Int, month: Int, date: Int) {
        set(year, month, date)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    constructor(year: Int, month: Int, date: Int, hrs: Int, min: Int, sec: Int) {
        set(year, month, date, hrs, min, sec)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    constructor(year: Int, month: Int, date: Int, hrs: Int, min: Int) {
        set(year, month, date, hrs, min)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    constructor(shDate: String) {
        set(shDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    constructor(shDate: String, timeMillis: Long) {
        set(shDate, timeMillis)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    constructor(shDate: String, hrs: Int, min: Int, sec: Int) {
        set(shDate, hrs, min, sec)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    constructor(shDate: String, hrs: Int, min: Int) {
        set(shDate, hrs, min)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    constructor(miDate: Date) {
        set(miDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    constructor(miDate: Date, timeMillis: Long) {
        set(miDate, timeMillis)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    constructor(miDate: Date, hrs: Int, min: Int, sec: Int) {
        set(miDate, hrs, min, sec)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    constructor(miDate: Date, hrs: Int, min: Int) {
        set(miDate, hrs, min)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun composite(year: Int, month: Int, date: Int): String {
        val ys: String
        val ms: String
        val ds: String
        val df = DecimalFormat()
        df.applyPattern("0000")
        ys = df.format(year.toLong())
        df.applyPattern("00")
        ms = df.format(month.toLong())
        df.applyPattern("00")
        ds = df.format(date.toLong())
        return "$ys/$ms/$ds"
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun getField(field: Int): Int {
        val gc = GregorianCalendar()
        gc.time = internalDate
        return gc[field]
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun setField(field: Int, newValue: Int) {
        val gc = GregorianCalendar()
        gc.time = internalDate
        gc[field] = newValue
        internalDate = gc.time
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun get(): Date? {
        return internalDate
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun set(millis: Long) {
        internalDate = Date(millis)
        internalShamsiDate = ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate!!)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun set() {
        internalDate = Date()
        internalShamsiDate = ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    operator fun set(year: Int, month: Int, date: Int) {
        internalDate = ShamsiCalendar.shamsiToMiladi_persiancoders(composite(year, month, date))
        internalShamsiDate = ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    operator fun set(year: Int, month: Int, date: Int, hrs: Int, min: Int, sec: Int) {
        internalDate = ShamsiCalendar.shamsiToMiladi_persiancoders(composite(year, month, date))
        setField(Calendar.HOUR_OF_DAY, hrs)
        setField(Calendar.MINUTE, min)
        setField(Calendar.SECOND, sec)
        internalShamsiDate = ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    operator fun set(year: Int, month: Int, date: Int, hrs: Int, min: Int) {
        internalDate = ShamsiCalendar.shamsiToMiladi_persiancoders(composite(year, month, date))
        setField(Calendar.HOUR_OF_DAY, hrs)
        setField(Calendar.MINUTE, min)
        internalShamsiDate = ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun set(shDate: String) {
        internalDate = ShamsiCalendar.shamsiToMiladi_persiancoders(shDate)
        internalShamsiDate = ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    operator fun set(shDate: String, timeMillis: Long) {
        internalDate = ShamsiCalendar.shamsiToMiladi_persiancoders(shDate)
        internalDate = Date(internalDate.getTime() + timeMillis)
        internalShamsiDate = ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    operator fun set(shDate: String, hrs: Int, min: Int, sec: Int) {
        internalDate = ShamsiCalendar.shamsiToMiladi_persiancoders(shDate)
        setField(Calendar.HOUR_OF_DAY, hrs)
        setField(Calendar.MINUTE, min)
        setField(Calendar.SECOND, sec)
        internalShamsiDate = ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    operator fun set(shDate: String, hrs: Int, min: Int) {
        internalDate = ShamsiCalendar.shamsiToMiladi_persiancoders(shDate)
        setField(Calendar.HOUR_OF_DAY, hrs)
        setField(Calendar.MINUTE, min)
        internalShamsiDate = ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun set(miDate: Date) {
        internalDate = miDate
        internalShamsiDate = ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    operator fun set(miDate: Date, timeMillis: Long) {
        internalDate = Date(miDate.time + timeMillis)
        internalShamsiDate = ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    operator fun set(miDate: Date, hrs: Int, min: Int, sec: Int) {
        internalDate = miDate
        setField(Calendar.HOUR_OF_DAY, hrs)
        setField(Calendar.MINUTE, min)
        setField(Calendar.SECOND, sec)
        internalShamsiDate = ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    operator fun set(miDate: Date, hrs: Int, min: Int) {
        internalDate = miDate
        setField(Calendar.HOUR_OF_DAY, hrs)
        setField(Calendar.MINUTE, min)
        internalShamsiDate = ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun setTime(hrs: Int, min: Int) {
        setField(Calendar.HOUR_OF_DAY, hrs)
        setField(Calendar.MINUTE, min)
    }

    fun setTime(hrs: Int, min: Int, sec: Int) {
        setField(Calendar.HOUR_OF_DAY, hrs)
        setField(Calendar.MINUTE, min)
        setField(Calendar.SECOND, sec)
    }

    val hour: Int
        get() = getField(Calendar.HOUR_OF_DAY)
    val minute: Int
        get() = getField(Calendar.MINUTE)
    val second: Int
        get() = getField(Calendar.SECOND)

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    val year: Int
        get() = ShamsiCalendar.getYear(ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate))
    val month: Int
        get() = ShamsiCalendar.getMonth(ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate))
    val date: Int
        get() = ShamsiCalendar.getDate(ShamsiCalendar.miladiToShamsi_persiancoders_com(internalDate))

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun dayOfWeek(): Int {
        return getField(Calendar.DAY_OF_WEEK)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun toMiladi(): Date? {
        return internalDate
    }

    override fun toString(): String {
        return internalShamsiDate!!
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun after(`when`: Date?): Boolean {
        return internalDate!!.after(`when`)
    }

    fun after(`when`: FDate): Boolean {
        return internalDate!!.after(`when`.get())
    }

    fun before(`when`: Date?): Boolean {
        return internalDate!!.before(`when`)
    }

    fun before(`when`: FDate): Boolean {
        return internalDate!!.before(`when`.get())
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun equals(`when`: Date): Boolean {
        return internalDate == `when`
    }

    fun equals(`when`: FDate): Boolean {
        return internalDate == `when`.get()
    }

    operator fun compareTo(`when`: Date?): Int {
        return internalDate!!.compareTo(`when`)
    }

    operator fun compareTo(`when`: FDate): Int {
        return internalDate!!.compareTo(`when`.get())
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    val millis: Long
        get() = internalDate!!.time

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun nextDay() {
        internalDate!!.time = internalDate!!.time + DAY_MILLIS
        internalShamsiDate = ShamsiCalendar.nextDay(internalShamsiDate)
    }

    fun prevDay() {
        internalDate!!.time = internalDate!!.time - DAY_MILLIS
        internalShamsiDate = ShamsiCalendar.prevDay(internalShamsiDate)
    }

    fun nextMonth() {
        internalShamsiDate = ShamsiCalendar.nextMonth(internalShamsiDate)
        internalDate = ShamsiCalendar.shamsiToMiladi_persiancoders(internalShamsiDate)
    }

    fun prevMonth() {
        internalShamsiDate = ShamsiCalendar.prevMonth(internalShamsiDate)
        set(ShamsiCalendar.shamsiToMiladi_persiancoders(internalShamsiDate), hour, minute, second)
    }

    fun nextYear() {
        internalShamsiDate = ShamsiCalendar.nextYear(internalShamsiDate)
        internalDate = ShamsiCalendar.shamsiToMiladi_persiancoders(internalShamsiDate)
    }

    fun prevYear() {
        internalShamsiDate = ShamsiCalendar.prevYear(internalShamsiDate)
        set(ShamsiCalendar.shamsiToMiladi_persiancoders(internalShamsiDate), hour, minute, second)
    }

    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //|
    //|
    //+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    fun plusDay(dayCount: Int) {
        internalShamsiDate = ShamsiCalendar.plusDay(internalShamsiDate, dayCount)
        set(ShamsiCalendar.shamsiToMiladi_persiancoders(internalShamsiDate), hour, minute, second)
    }

    fun minusDay(dayCount: Int) {
        internalShamsiDate = ShamsiCalendar.minusDay(internalShamsiDate, dayCount)
        set(ShamsiCalendar.shamsiToMiladi_persiancoders(internalShamsiDate), hour, minute, second)
    }

    fun minusDate(shDate: String): Int {
        val m1 = millis
        val m2 = FDate(shDate).millis
        var diff = m1 - m2
        if (diff < 0) diff *= -1
        return ShamsiCalendar.millisToDay(diff).toInt()
    }

    companion object {
        protected const val DAY_MILLIS = (1000 * 60 * 60 * 24).toLong()
        protected const val HOUR_MILLIS = 1000 * 60 * 60
        protected const val MINUTE_MILLIS = 1000 * 60
        fun diffDate(shDt1: String, shDt2: String): Int {
            val fd = FDate(shDt1)
            return fd.minusDate(shDt2)
        }
    }
}