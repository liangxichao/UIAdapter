package xc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

/**
 * @author lxc
 * time at 2018/6/15 11:21
 */
public class SampleAct_3 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_3);

        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new MyAdapter());
        // gridView.setHorizontalSpacing((int) TDLayoutMgr.getActualPX(30));
        // gridView.setVerticalSpacing((int) TDLayoutMgr.getActualPX(30));
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int i) {
            return "";
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(SampleAct_3.this).inflate(R.layout.item_grid_view, viewGroup, false);
            }
            return view;
        }
    }
}
