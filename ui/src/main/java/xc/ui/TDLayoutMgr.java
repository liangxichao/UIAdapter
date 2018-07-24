package xc.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import xc.ui.views.UIParams;

/**
 * 布局管理
 *
 * @author lxc
 * time at 2016/11/11
 */
public class TDLayoutMgr {

    /**
     * 当前的 UI 是基于 1334*750 尺寸设计的， 以下的一些默认值可自定义
     * 当前的 UI 适配是基于 目标尺寸 与 当前屏幕的 参考高度{@link #referHeight} 来实现的
     */
    public static final int DESIGN_HEIGHT = 1334;

    /**
     * 适配的参考高度 参考宽度
     * 这两个高度是动态计算的 {@link #init}
     */
    public static int referHeight = 1334;
    public static int referWidth = 750;

    // 当前屏幕实际的宽度
    public static int screenW;

    // 当前屏幕实际的高度
    public static int screenH = 1334;

    public static boolean isPad;

    private TDLayoutMgr() {}

    public static void init(Context context) {
        if (TDLayoutMgr.screenW > 0)
            return;

        screenW = ScreenUtils.getScreenWidth(context);
        screenH = ScreenUtils.getScreenRealHeight(context);

        double rate = MathUtil.div(screenH, screenW);

        // 平板与手机的屏幕标准比例 （可根据需求自定义）
        double standardRatePhone = MathUtil.div(16, 9);
        double standardRatePad = MathUtil.div(4, 3);

        if (rate >= 1.59f) {
            /**
             * 接近手机的设备
             */
            double standardHeight = MathUtil.mul(screenW, standardRatePhone);
            if (rate > standardRatePhone) {
                referHeight = (int) standardHeight;
            } else {
                referHeight = screenH;
            }

        } else {
            /**
             * 接近平板的设备
             */
            double standardHeight = MathUtil.mul(screenW, standardRatePad);
            if (rate > standardRatePad) {
                referHeight = (int) standardHeight;
            } else {
                referHeight = screenH;
            }

            isPad = true;
        }

        // 这里的相对宽度是以手机屏幕为标准的
        referWidth = (int) MathUtil.div(referHeight, standardRatePhone);
    }

    public static ViewGroup.LayoutParams setViewMargin(View target, float left, float right, float top, float bottom) {
        return setViewSize(target, -1, -1, left, right, top, bottom);
    }

    public static ViewGroup.LayoutParams setViewSize(View target, float width, float height) {
        return setViewSize(target, width, height, 0, 0, 0, 0);
    }

    public static void setViewPadding(View target, float left, float right, float top, float bottom) {
        left = (int) getActualPX(left);
        right = (int) getActualPX(right);
        top = (int) getActualPX(top);
        bottom = (int) getActualPX(bottom);

        if (left == UIParams.UI_DEFAULT_PARAM) {
            left = target.getPaddingLeft();
        }

        if (right == UIParams.UI_DEFAULT_PARAM) {
            right = target.getPaddingRight();
        }

        if (top == UIParams.UI_DEFAULT_PARAM) {
            top = target.getPaddingTop();
        }

        if (bottom == UIParams.UI_DEFAULT_PARAM) {
            bottom = target.getPaddingBottom();
        }

        target.setPadding((int) left, (int) top, (int) right, (int) bottom);
    }

    /**
     * 这里的值的单位 px
     */
    public static ViewGroup.LayoutParams setViewSize(View target, float width, float height,
                                                     float left, float right,
                                                     float top, float bottom) {

        ViewGroup.LayoutParams temp = target.getLayoutParams();
        if (temp == null)
            return null;

        if (temp instanceof ViewGroup.MarginLayoutParams) {

            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) temp;
            if (width >= 0) {
                lp.width = (int) getActualPX(width);
            }

            if (height >= 0) {
                lp.height = (int) getActualPX(height);
            }

            left = (int) getActualPX(left);
            right = (int) getActualPX(right);
            top = (int) getActualPX(top);
            bottom = (int) getActualPX(bottom);

            if (left != UIParams.UI_DEFAULT_PARAM) {
                lp.leftMargin = (int) left;
            }

            if (right != UIParams.UI_DEFAULT_PARAM) {
                lp.rightMargin = (int) right;
            }

            if (top != UIParams.UI_DEFAULT_PARAM) {
                lp.topMargin = (int) top;
            }

            if (bottom != UIParams.UI_DEFAULT_PARAM) {
                lp.bottomMargin = (int) bottom;
            }

            target.setLayoutParams(lp);
        }

        return temp;
    }

    /**
     * 获取实际在设备上显示的尺寸大小（单位：像素）
     * @param size ui 设计的尺寸大小
     * @return 际在设备上显示的数值大小
     */
    public static float getActualPX(float size) {
        if (size <= 0)
            return size;

        return (float) (referHeight * MathUtil.div(size, DESIGN_HEIGHT));
    }
}
