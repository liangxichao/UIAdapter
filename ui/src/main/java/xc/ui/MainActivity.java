package xc.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import xc.ui.BaseActivity;

/**
 * @author lxc
 * time at 2018/6/15 9:57
 */
public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSample(View view) {
        startActivity(new Intent(this, SampleAct_1.class));
    }

    public void onSample2(View view) {
        startActivity(new Intent(this, SampleAct_2.class));
    }

    public void onSample3(View view) {
        startActivity(new Intent(this, SampleAct_3.class));
    }
}
