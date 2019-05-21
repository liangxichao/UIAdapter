package xc.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import xc.ui.views.UIView

/**
 * @author lxc
 * time at 2019/5/20 16:58
 */
class AnimView(context: Context?, attrs: AttributeSet?) : UIView(context, attrs) {
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var wh = 0
    private var anims: ArrayList<AnimInfo> = ArrayList()
    // 动画单元的宽度
    private var animW: Float = 0f
    // 动画单元之间的横向间隔
    private var space: Float = 0f
    // 动画单元的个数
    private var animSize = 5
    private var maxProgress: Float = 0f
    private var minProgress: Float = 0f

    init {
        paint.color = Color.parseColor("#fff4511e")
    }

    private fun initAnims() {
        for (i in 0..(animSize - 1)) {
            val x = animW * i + (i + 1) * space
            var defaultProgress = 0f
            when (i) {
                0, 4 -> defaultProgress = maxProgress / 3
                2 -> defaultProgress = maxProgress
                1, 3 -> defaultProgress = maxProgress * 2 / 3
            }
            val anim = AnimInfo(this, animW, x, maxProgress, minProgress,
                    defaultProgress, paint)
            anims.add(anim)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (wh == 0) {
            wh = width
            space = wh * 0.1f
            animW = (wh - space * (animSize + 1)) / animSize
            maxProgress = wh * 0.8f
            minProgress = maxProgress * 0.3f

            initAnims()

            if (isDelyStartAnim) {
                startAnim()
                return
            }
        }

        if (anims.isEmpty())
            return

        for (anim in anims) {
            anim.draw(canvas)
        }


    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (!anims.isEmpty()) {
            for (anim in anims) {
                anim.reset()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Handler().postDelayed({
            startAnim()
        }, 1000)
    }

    private var isDelyStartAnim = false
    private fun startAnim() {
        if (width == 0) {
            isDelyStartAnim = true
            return
        }

        isDelyStartAnim = false

        if (!anims.isEmpty()) {
            for (anim in anims) {
                anim.reset()
            }
        }

        for (anim in anims) {
            anim.start()
        }
    }

    internal class AnimInfo(var targetView: View, animW: Float, var x: Float,
                            var maxProgress: Float, var minProgress: Float,
                            var defaultProgress: Float, var paint: Paint) {
        private var progress = 10f
        private var rectF: RectF
        private var roundXY = 0f
        private var viewH: Int
        private var animSet: AnimatorSet? = null

        init {
            progress = defaultProgress
            viewH = targetView.height
            roundXY = animW / 2f
            val top = (viewH - progress) / 2f
            rectF = RectF(x, (viewH - progress) / 2f, x + animW,
                    top + progress)
        }

        fun draw(canvas: Canvas?) {
            rectF.top = (viewH - progress) / 2f
            rectF.bottom = rectF.top + progress
            canvas?.drawRoundRect(rectF, roundXY, roundXY, paint)
        }

        fun start(isInit: Boolean = true) {
            reset()

            val va = ValueAnimator.ofFloat(
                    if (isInit)
                        defaultProgress
                    else
                        progress,

                    if (isInit)
                        maxProgress
                    else
                        minProgress)

            va.addUpdateListener {
                progress = it.animatedValue as Float
                targetView.invalidate()
            }

            if (isInit) {
                va.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        start(false)
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                    }

                    override fun onAnimationStart(p0: Animator?) {
                    }

                })
            }

            if (isInit) {
                va.duration = (1500 * (1 - defaultProgress / maxProgress)).toLong()
                val speed = (maxProgress - defaultProgress) / va.duration
                println("defaultProgress = ${defaultProgress} duration = ${va.duration} speed = ${speed}")
                if (va.duration == 0L) {
                    va.duration = 1
                }
                va.startDelay = 100
            } else {
                va.duration = 1500
                va.repeatCount = ValueAnimator.INFINITE
                va.repeatMode = ValueAnimator.REVERSE
            }

            val set = AnimatorSet()
            set.play(va)
            set.start()

            if (!isInit) {
                this.animSet = set
            }
        }

        /**
         * 动画重置
         */
        fun reset() {
            if (animSet == null)
                return

            if (animSet?.isRunning ?: false)
                return

            animSet?.cancel()
        }
    }
}




