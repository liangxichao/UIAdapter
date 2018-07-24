package xc.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @author lxc
 * time at 2016/11/11
 */
public class UITextView extends android.support.v7.widget.AppCompatTextView {
    public UIParams uip;

    public UITextView(Context context, AttributeSet attrs) {
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
}
