package customView.space

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat

class SunSetView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    var sun_angle = 12
    private var SunstartTime = "2019-09-06 05:38:00"
    private var SunendTime = "2019-09-06 18:16:00"
    fun setSunstartTime(sunstartTime: String) {
        SunstartTime = sunstartTime
        invalidate()
    }

    fun setSunendTime(sunendTime: String) {
        SunendTime = sunendTime
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()


//        mPaint.
        initPaint()
        canvas.drawLine(0 + mWidth / 12.toFloat(), mHeight - mWidth / 6
                .toFloat(), mWidth - mWidth / 12.toFloat(), mHeight - mWidth / 6.toFloat(), mPaint!!)
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.textSize = 35f
        mPaint!!.strokeWidth = 1f
        val startTime = getTimeText(SunstartTime)
        val endTime = getTimeText(SunendTime)
        val rect = Rect()
        mPaint!!.getTextBounds(startTime, 0, startTime.length, rect)
        val w = rect.width()
        val h = rect.height() * 2
        canvas.drawText(startTime, mWidth / 6 - w / 2.toFloat(), mHeight - mWidth / 6 + h.toFloat(), mPaint!!)
        canvas.drawText(endTime, mWidth - mWidth / 6 - (w / 2).toFloat(), mHeight - mWidth / 6 + h.toFloat(), mPaint!!)
        mPaint!!.style = Paint.Style.STROKE //设置空心
        mPaint!!.strokeWidth = 2.5f
        mPaint!!.pathEffect = DashPathEffect(floatArrayOf(14f, 12f), 0f)
        val oval1 = RectF((mWidth / 2 - mWidth / 3).toFloat(), ((mHeight - mWidth / 6 - mWidth / 3).toFloat()
                + Math.sin(Math.toRadians(sun_angle.toDouble())) * mWidth / 3).toFloat()
                ,
                (mWidth / 2 + mWidth / 3).toFloat(), (mHeight - mWidth / 6 + mWidth / 3 + Math.sin(Math.toRadians(sun_angle.toDouble())) * mWidth / 3).toFloat())
        canvas.drawArc(oval1, 180 + sun_angle.toFloat(), (180 - 2 * sun_angle).toFloat(), false, mPaint!!) //小弧形
        var intervalf = 0f
        try {
            val TodayInterval = getTimeInterval(SunstartTime, SunendTime)
            val ts = Timestamp(System.currentTimeMillis())
            val nowtime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts.time)
            val nowInterval = getTimeInterval(SunstartTime, nowtime)
            intervalf = nowInterval * 1.0f / TodayInterval * mAnimatedValue
            if (intervalf > 1f) {
                intervalf = 1f
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }


//        float y = (float) (Math.sin(Math.toRadians(sun_angle + (180 - 2 * sun_angle) * intervalf)) * mWidth / 3);
//        float x = (float) (Math.cos(Math.toRadians(sun_angle + (180 - 2 * sun_angle) * intervalf)) * mWidth / 3);
//        mPaint.setStyle(Paint.Style.FILL);//设置空心
//        canvas.drawBitmap(getSun(intervalf), mWidth / 2 - x - mWidth / 3 / 4, (float) (mHeight - mWidth / 6 - y - mWidth / 3 / 4 + Math.sin(Math.toRadians(sun_angle)) * mWidth / 3), mPaint);
        canvas.drawBitmap(getSunbg(intervalf), 0f, 0f, mPaint)
        canvas.restore()
    }

    var mPaint: Paint? = null
    fun startSunset() {
        mhandler.obtainMessage(0).sendToTarget()
        val m = Message()
        m.what = 1
        mhandler.sendMessageDelayed(m, 200)
    }

    fun start() {
        stopAnim()
        var intervalf = 0f
        try {
            val TodayInterval = getTimeInterval(SunstartTime, SunendTime)
            val ts = Timestamp(System.currentTimeMillis())
            val nowtime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts.time)
            val nowInterval = getTimeInterval(SunstartTime, nowtime)
            intervalf = nowInterval * 1.0f / TodayInterval
            if (intervalf > 1f) {
                intervalf = 1f
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        startViewAnim(0f, 1f, (4000 * intervalf).toLong())
    }

    private fun initPaint() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.color = Color.WHITE
        mPaint!!.strokeWidth = 4f
    }

    var mWidth = 0
    var mHeight = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mHeight = measuredHeight
        mWidth = measuredWidth
    }

    private var valueAnimator: ValueAnimator? = null
    private var mAnimatedValue = 0f
    fun stopAnim() {
        if (valueAnimator != null) {
            clearAnimation()
            valueAnimator!!.repeatCount = 0
            valueAnimator!!.cancel()
            mAnimatedValue = 0f
            postInvalidate()
        }
    }

    private fun startViewAnim(startF: Float, endF: Float, time: Long): ValueAnimator? {
        valueAnimator = ValueAnimator.ofFloat(startF, endF)
        valueAnimator?.setDuration(time)
        valueAnimator?.setInterpolator(LinearInterpolator())
        valueAnimator?.setRepeatCount(0)
        valueAnimator?.setRepeatMode(ValueAnimator.RESTART)
        valueAnimator?.addUpdateListener(AnimatorUpdateListener { valueAnimator ->
            mAnimatedValue = valueAnimator.animatedValue as Float
            invalidate()
        })
        valueAnimator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
            }

            override fun onAnimationRepeat(animation: Animator) {
                super.onAnimationRepeat(animation)
            }
        })
        if (valueAnimator?.isRunning == false) {
            valueAnimator?.start()
        }
        return valueAnimator
    }

    @Throws(ParseException::class)
    fun getTimeInterval(oldTime: String?, newTime: String?): Long {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val NTime = df.parse(newTime).time
        //从对象中拿到时间
        val OTime = df.parse(oldTime).time
        return (NTime - OTime) / 1000 / 60
    }

    private fun getTimeText(tsStr: String): String {
        var ts = Timestamp(System.currentTimeMillis())
        //        String tsStr = "2011-05-09 11:49:45";
        var time = "00:00"
        try {
            ts = Timestamp.valueOf(tsStr)
            time = SimpleDateFormat("HH:mm").format(ts.time)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return time
    }

    private fun getSunbg(intervalf: Float): Bitmap {
        val b = Bitmap.createBitmap(mWidth, mHeight - mWidth / 6, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        c.save()
        mPaint!!.style = Paint.Style.STROKE //设置空心
        mPaint!!.color = Color.argb(255, 254, 219, 57)
        if (intervalf < 1f) {
            val oval2 = RectF((mWidth / 2 - mWidth / 3).toFloat(), ((mHeight - mWidth / 6 - mWidth / 3).toFloat()
                    + Math.sin(Math.toRadians(sun_angle.toDouble())) * mWidth / 3).toFloat()
                    ,
                    (mWidth / 2 + mWidth / 3).toFloat(), (mHeight - mWidth / 6 + mWidth / 3 + Math.sin(Math.toRadians(sun_angle.toDouble())) * mWidth / 3).toFloat())
            c.drawArc(oval2, 180 + sun_angle.toFloat(), (180 - 2 * sun_angle) * intervalf, false, mPaint!!) //小弧形
        }
        mPaint!!.style = Paint.Style.FILL //设置空心
        //如果从地下开始日出这个 sun_angle用0使用
        val y = (Math.sin(Math.toRadians(sun_angle + (180 - 2 * sun_angle) * intervalf.toDouble())) * mWidth / 3).toFloat()
        val x = (Math.cos(Math.toRadians(sun_angle + (180 - 2 * sun_angle) * intervalf.toDouble())) * mWidth / 3).toFloat()
        c.drawBitmap(getSun(intervalf), mWidth / 2 - x - mWidth / 3 / 4, (mHeight - mWidth / 6 - y - mWidth / 3 / 4 + Math.sin(Math.toRadians(sun_angle.toDouble())) * mWidth / 3).toFloat(), mPaint)
        c.restore()
        return b
    }

    private fun getSun(intervalf: Float): Bitmap {
//        intervalf = 0;
        val b = Bitmap.createBitmap(mWidth / 3 / 2, mWidth / 3 / 2, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        c.save()
        mPaint!!.style = Paint.Style.FILL //设置空心
        c.rotate(intervalf * 180, mWidth / 3 / 4.toFloat(), mWidth / 3 / 4.toFloat())
        if (intervalf >= 0.3f || intervalf <= 0.7f) {
            mPaint!!.color = Color.argb(255, 254, 219, 57)
        }
        if (intervalf < 0.3f) {
            val suncolor = (230 + 25 * (intervalf / 0.3f)).toInt()
            mPaint!!.color = Color.argb(suncolor, 254, 219, 57)
        } else if (intervalf > 0.7f) {
            val suncolor = (230 + 25 * ((1f - intervalf) / 0.3f)).toInt()
            mPaint!!.color = Color.argb(suncolor, 254, 219, 57)
        }
        if (intervalf > 0 && intervalf < 1) {
            c.drawCircle(mWidth / 3 / 4.toFloat(), mWidth / 3 / 4.toFloat(), mWidth / 3 / 10.toFloat(), mPaint!!)
            mPaint!!.strokeWidth = 5f
            c.drawLine(mWidth / 3 / 4 - mWidth / 3 / 10 - (mWidth / 3 / 10 / 5).toFloat(),
                    mWidth / 3 / 4.toFloat(),
                    mWidth / 3 / 4 - mWidth / 3 / 10 - mWidth / 3 / 10 / 5 - (mWidth / 3 / 10 / 2)
                            .toFloat(), mWidth / 3 / 4.toFloat(), mPaint!!
            )
            c.drawLine(mWidth / 3 / 4 + mWidth / 3 / 10 + (mWidth / 3 / 10 / 5).toFloat(),
                    mWidth / 3 / 4.toFloat(),
                    mWidth / 3 / 4 + mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + (mWidth / 3 / 10 / 2)
                            .toFloat(), mWidth / 3 / 4.toFloat(), mPaint!!
            )
            c.drawLine(mWidth / 3 / 4.toFloat(), mWidth / 3 / 4 - mWidth / 3 / 10 - (mWidth / 3 / 10 / 5)
                    .toFloat(), mWidth / 3 / 4.toFloat(),
                    mWidth / 3 / 4 - mWidth / 3 / 10 - mWidth / 3 / 10 / 5 - (mWidth / 3 / 10 / 2)
                            .toFloat(), mPaint!!
            )
            c.drawLine(mWidth / 3 / 4.toFloat(), mWidth / 3 / 4 + mWidth / 3 / 10 + (mWidth / 3 / 10 / 5)
                    .toFloat(), mWidth / 3 / 4.toFloat(),
                    mWidth / 3 / 4 + mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + (mWidth / 3 / 10 / 2)
                            .toFloat(), mPaint!!
            )
            c.drawLine((mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , (mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , mPaint!!
            )
            c.drawLine((mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , (mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , mPaint!!
            )
            c.drawLine((mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , (mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , mPaint!!
            )
            c.drawLine((mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , (mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , mPaint!!
            )


//            Log.e("eeeee", (Math.sqrt(2) + ""));
        } else if (intervalf == 0f) {
            val oval1 = RectF()
            oval1[mWidth / 3 / 4 - mWidth / 3 / 10.toFloat(), mWidth / 3 / 4 - mWidth / 3 / 10.toFloat(), mWidth / 3 / 4 + mWidth / 3 / 10.toFloat()] = mWidth / 3 / 4 + mWidth / 3 / 10.toFloat()
            c.drawArc(oval1, 180f, 180f, true, mPaint!!) //小弧形
            mPaint!!.strokeWidth = 5f
            c.drawLine(mWidth / 3 / 4 - mWidth / 3 / 10 - (mWidth / 3 / 10 / 5).toFloat(),
                    mWidth / 3 / 4.toFloat(),
                    mWidth / 3 / 4 - mWidth / 3 / 10 - mWidth / 3 / 10 / 5 - (mWidth / 3 / 10 / 2)
                            .toFloat(), mWidth / 3 / 4.toFloat(), mPaint!!
            )
            c.drawLine(mWidth / 3 / 4.toFloat(), mWidth / 3 / 4 - mWidth / 3 / 10 - (mWidth / 3 / 10 / 5)
                    .toFloat(), mWidth / 3 / 4.toFloat(),
                    mWidth / 3 / 4 - mWidth / 3 / 10 - mWidth / 3 / 10 / 5 - (mWidth / 3 / 10 / 2)
                            .toFloat(), mPaint!!
            )
            c.drawLine(mWidth / 3 / 4 + mWidth / 3 / 10 + (mWidth / 3 / 10 / 5).toFloat(),
                    mWidth / 3 / 4.toFloat(),
                    mWidth / 3 / 4 + mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + (mWidth / 3 / 10 / 2)
                            .toFloat(), mWidth / 3 / 4.toFloat(), mPaint!!
            )
            c.drawLine((mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , (mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , mPaint!!
            )
            c.drawLine((mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , (mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , mPaint!!
            )
        } else if (intervalf == 1f) {
            mPaint!!.strokeWidth = 5f
            val oval1 = RectF()
            oval1[mWidth / 3 / 4 - mWidth / 3 / 10.toFloat(), mWidth / 3 / 4 - mWidth / 3 / 10.toFloat(), mWidth / 3 / 4 + mWidth / 3 / 10.toFloat()] = mWidth / 3 / 4 + mWidth / 3 / 10.toFloat()
            c.drawArc(oval1, 180f, -180f, true, mPaint!!) //小弧形
            c.drawLine(mWidth / 3 / 4 - mWidth / 3 / 10 - (mWidth / 3 / 10 / 5).toFloat(),
                    mWidth / 3 / 4.toFloat(),
                    mWidth / 3 / 4 - mWidth / 3 / 10 - mWidth / 3 / 10 / 5 - (mWidth / 3 / 10 / 2)
                            .toFloat(), mWidth / 3 / 4.toFloat(), mPaint!!
            )
            c.drawLine(mWidth / 3 / 4.toFloat(), mWidth / 3 / 4 + mWidth / 3 / 10 + (mWidth / 3 / 10 / 5)
                    .toFloat(), mWidth / 3 / 4.toFloat(),
                    mWidth / 3 / 4 + mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + (mWidth / 3 / 10 / 2)
                            .toFloat(), mPaint!!
            )
            c.drawLine(mWidth / 3 / 4 + mWidth / 3 / 10 + (mWidth / 3 / 10 / 5).toFloat(),
                    mWidth / 3 / 4.toFloat(),
                    mWidth / 3 / 4 + mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + (mWidth / 3 / 10 / 2)
                            .toFloat(), mWidth / 3 / 4.toFloat(), mPaint!!
            )
            c.drawLine((mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 - (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , (mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , mPaint!!
            )
            c.drawLine((mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5).toFloat() / Math.sqrt(2.0)).toFloat(),
                    (mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , (mWidth / 3 / 4 + (mWidth / 3 / 10 + mWidth / 3 / 10 / 5 + mWidth / 3 / 10 / 2).toFloat() / Math.sqrt(2.0)).toFloat()
                    , mPaint!!
            )
        }
        c.restore()
        return b
    }

    private val mhandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                mAnimatedValue = 0f
                invalidate()
            } else if (msg.what == 1) {
                start()
            }
        }
    }
}