package xc.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author lxc
 * time at 2018/6/15 9:58
 */
public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 该句代码会多次调用，但只会执行一次
        TDLayoutMgr.init(this);
    }
}
