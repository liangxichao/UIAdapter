package xc.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author lxc
 * time at 2016/11/11
 */
public class UIImageView extends android.support.v7.widget.AppCompatImageView implements View.OnTouchListener {
    public UIParams uip;

    public UIImageView(Context context) {
        super(context);
    }
    public UIImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        uip = new UIParams(context, attrs);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        if (uip != null) {
            super.setLayoutParams(uip.updateLayoutParams(this, params));
        } else {
            super.setLayoutParams(params);
        }
    }

    private boolean isBackgroundEffect;
    public void setClickEffectEnable(boolean isBackgroundEffect) {
        this.isBackgroundEffect = isBackgroundEffect;
        setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Drawable d = v.getBackground();
                if (!isBackgroundEffect) {
                    d = ((ImageView) v).getDrawable();
                }

                d.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Drawable d = v.getBackground();
                if (!isBackgroundEffect) {
                    d = ((ImageView) v).getDrawable();
                }

                d.clearColorFilter();
                break;
        }
        return false;
    }
}
