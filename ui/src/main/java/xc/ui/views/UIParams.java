package xc.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import xc.ui.R;
import xc.ui.LayoutMgr;

/**
 * 自定义布局参数
 * 该自定义的布局参数会覆盖原生的布局参数
 * 建议 这些 ui 属性值 应和 ui 设计图上标记的尺寸大小保持一致
 *
 * @author lxc
 * time at 2016/11/11
 */
public class UIParams {
    /** 此默认值必须为负数  {@link LayoutMgr#getActualPX(float)} 方法的实现决定的 */
    public static final float UI_DEFAULT_PARAM = Integer.MIN_VALUE;

    public float width, height;
    public float pLeft, pRight, pTop, pBottom;
    public float mLeft, mRight, mTop, mBottom;
    public float textSize;
    public float drawablePadding;
    public float drawableWidth, drawableHeight;
    public float minHeight, minWidth;
    public float dividerHeight;
    public float horizontalSpacing, verticalSpacing;

    // 行距
    private float lineSpacing;

    private boolean isInit = true;

    public UIParams(Context context, AttributeSet attrs) {
        isInit = true;

        //初始化控件属性
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.UIView);
        width = arr.getFloat(R.styleable.UIView_ui_width, -1);
        height = arr.getFloat(R.styleable.UIView_ui_height, -1);

        float margin = arr.getFloat(R.styleable.UIView_ui_margin, UI_DEFAULT_PARAM);
        if (margin == UI_DEFAULT_PARAM) {
            mLeft = arr.getFloat(R.styleable.UIView_ui_marginLeft, UI_DEFAULT_PARAM);
            mRight = arr.getFloat(R.styleable.UIView_ui_marginRight, UI_DEFAULT_PARAM);
            mTop = arr.getFloat(R.styleable.UIView_ui_marginTop, UI_DEFAULT_PARAM);
            mBottom = arr.getFloat(R.styleable.UIView_ui_marginBottom, UI_DEFAULT_PARAM);
        } else {
            mLeft = margin;
            mRight = margin;
            mTop = margin;
            mBottom = margin;
        }

        float padding = arr.getFloat(R.styleable.UIView_ui_padding, UI_DEFAULT_PARAM);
        if (padding == UI_DEFAULT_PARAM) {
            pLeft = arr.getFloat(R.styleable.UIView_ui_paddingLeft, UI_DEFAULT_PARAM);
            pRight = arr.getFloat(R.styleable.UIView_ui_paddingRight, UI_DEFAULT_PARAM);
            pTop = arr.getFloat(R.styleable.UIView_ui_paddingTop, UI_DEFAULT_PARAM);
            pBottom = arr.getFloat(R.styleable.UIView_ui_paddingBottom, UI_DEFAULT_PARAM);
        } else {
            pLeft = padding;
            pRight = padding;
            pTop = padding;
            pBottom = padding;
        }

        textSize = arr.getFloat(R.styleable.UIView_ui_textSize, UI_DEFAULT_PARAM);
        drawableWidth = arr.getFloat(R.styleable.UIView_ui_drawableWidth, UI_DEFAULT_PARAM);
        drawableHeight = arr.getFloat(R.styleable.UIView_ui_drawableHeight, UI_DEFAULT_PARAM);
        drawablePadding = arr.getFloat(R.styleable.UIView_ui_drawablePadding, UI_DEFAULT_PARAM);

        enablePadMarginLeft = arr.getBoolean(R.styleable.UIView_ui_enablePadMarginLeft, false);
        enablePadMarginRight = arr.getBoolean(R.styleable.UIView_ui_enablePadMarginRight, false);
        enablePadPaddingLeft = arr.getBoolean(R.styleable.UIView_ui_enablePadPaddingLeft, false);
        enablePadPaddingRight = arr.getBoolean(R.styleable.UIView_ui_enablePadPaddingRight, false);

        lineSpacing = arr.getFloat(R.styleable.UIView_ui_lineSpacing, UI_DEFAULT_PARAM);
        minHeight = arr.getFloat(R.styleable.UIView_ui_minHeight, UI_DEFAULT_PARAM);
        minWidth = arr.getFloat(R.styleable.UIView_ui_minWidth, UI_DEFAULT_PARAM);

        dividerHeight = arr.getFloat(R.styleable.UIView_ui_dividerHeight, UIParams.UI_DEFAULT_PARAM);
        if (dividerHeight == UIParams.UI_DEFAULT_PARAM) {
            dividerHeight = 0;
        }

        horizontalSpacing = arr.getFloat(R.styleable.UIView_ui_horizontalSpacing, UIParams.UI_DEFAULT_PARAM);
        if (horizontalSpacing == UIParams.UI_DEFAULT_PARAM) {
            horizontalSpacing = 0;
        }

        verticalSpacing = arr.getFloat(R.styleable.UIView_ui_verticalSpacing, UIParams.UI_DEFAULT_PARAM);
        if (verticalSpacing == UIParams.UI_DEFAULT_PARAM) {
            verticalSpacing = 0;
        }

        arr.recycle();
    }

    public boolean enablePadMarginLeft, enablePadMarginRight;
    public boolean enablePadPaddingLeft, enablePadPaddingRight;

    public void setViewPadding(View target, float left, float right, float top, float bottom) {
        left = (int) LayoutMgr.getActualPX(left);
        right = (int) LayoutMgr.getActualPX(right);
        top = (int) LayoutMgr.getActualPX(top);
        bottom = (int) LayoutMgr.getActualPX(bottom);

        int p = (LayoutMgr.screenW - LayoutMgr.referWidth) / 2;
        if (p < 0) {
            p = 0;
        }

        if (left == UIParams.UI_DEFAULT_PARAM) {
            left = target.getPaddingLeft();
        }

        if (enablePadPaddingLeft) {
            left += p;
        }

        if (right == UIParams.UI_DEFAULT_PARAM) {
            right = target.getPaddingRight();
        }

        if (enablePadPaddingRight) {
            right += p;
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
     * 这里的值的单位 dp
     */
    private ViewGroup.LayoutParams setViewSize(ViewGroup.LayoutParams temp, float width, float height,
                                               float left, float right,
                                               float top, float bottom) {

        int p = (LayoutMgr.screenW - LayoutMgr.referWidth) / 2;
        if (p < 0) {
            p = 0;
        }

        if (width >= 0) {
            temp.width = (int) LayoutMgr.getActualPX(width);
        }

        if (height >= 0) {
            temp.height = (int) LayoutMgr.getActualPX(height);
        }

        if (temp instanceof ViewGroup.MarginLayoutParams) {

            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) temp;

            left = (int) LayoutMgr.getActualPX(left);
            right = (int) LayoutMgr.getActualPX(right);
            top = (int) LayoutMgr.getActualPX(top);
            bottom = (int) LayoutMgr.getActualPX(bottom);

            if (left != UIParams.UI_DEFAULT_PARAM) {
                lp.leftMargin = (int) left;
            }

            if (enablePadMarginLeft) {
                lp.leftMargin += p;
            }

            if (right != UIParams.UI_DEFAULT_PARAM) {
                lp.rightMargin = (int) right;
            }

            if (enablePadMarginRight) {
                lp.rightMargin += p;
            }

            if (top != UIParams.UI_DEFAULT_PARAM) {
                lp.topMargin = (int) top;
            }

            if (bottom != UIParams.UI_DEFAULT_PARAM) {
                lp.bottomMargin = (int) bottom;
            }
        }

        return temp;
    }

    public ViewGroup.LayoutParams updateLayoutParams(View view, ViewGroup.LayoutParams params) {
        if (isInit) {
            isInit = false;
            params = setViewSize(params, width, height, mLeft, mRight, mTop, mBottom);

            setViewPadding(view, pLeft, pRight, pTop, pBottom);

            if (minHeight != UI_DEFAULT_PARAM) {
                view.setMinimumHeight((int) LayoutMgr.getActualPX(minHeight));
            }

            if (minWidth != UI_DEFAULT_PARAM) {
                view.setMinimumWidth((int) LayoutMgr.getActualPX(minWidth));
            }

            if (view instanceof TextView) {
                TextView textView = (TextView) view;

                int right = (int) LayoutMgr.getActualPX(drawableWidth);
                int bottom = (int) LayoutMgr.getActualPX(drawableHeight);

                if (right > 0 && bottom > 0) {
                    Drawable[] ds = textView.getCompoundDrawables();
                    Drawable dLeft = ds[0];
                    if (dLeft != null) {
                        dLeft.setBounds(0, 0, right, bottom);
                    }

                    Drawable dTop = ds[1];
                    if (dTop != null) {
                        dTop.setBounds(0, 0, right, bottom);
                    }

                    Drawable dRight = ds[2];
                    if (dRight != null) {
                        dRight.setBounds(0, 0, right, bottom);
                    }

                    Drawable dBottom = ds[3];
                    if (dBottom != null) {
                        dBottom.setBounds(0, 0, right, bottom);
                    }

                    textView.setCompoundDrawables(dLeft, dTop, dRight, dBottom);
                }

                if (drawablePadding >= 0) {
                    textView.setCompoundDrawablePadding((int) LayoutMgr.getActualPX(drawablePadding));
                }

                if (textSize != UI_DEFAULT_PARAM) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, LayoutMgr.getActualPX(textSize));
                }

                if (lineSpacing != UI_DEFAULT_PARAM) {
                    textView.setLineSpacing(LayoutMgr.getActualPX(lineSpacing), 1f);
                }
            }

            if (view instanceof ListView) {
                ((ListView)view).setDividerHeight((int) LayoutMgr.getActualPX(dividerHeight));
            }

            if (view instanceof GridView) {
                ((GridView)view).setHorizontalSpacing((int) LayoutMgr.getActualPX(horizontalSpacing));
                ((GridView)view).setVerticalSpacing((int) LayoutMgr.getActualPX(verticalSpacing));
            }

            return params;
        }

        return params;
    }
}
