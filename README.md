# UIAdapter

# 用法：
在工程的的基类 Activity 的 onCreate 生命周期方法里添加：
``````
    LayoutMgr.init(this);
    如：
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // 该句代码会多次调用，但只会执行一次
            LayoutMgr.init(this);
        }

        导入自定义控件：
        views 目录下的
        ![](https://github.com/liangxichao/UIAdapter/blob/master/art/art_1.png)

        这是只是包括了常用的一些控件，其他的控件可以依照如下的模板来继承
        public class 自定义类名 extends 安卓控件类名 {
            protected UIParams uip;

            public 自定义类名(Context context, AttributeSet attrs) {
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
即可
``````
      
# 效果 #
![](https://github.com/liangxichao/UIAdapter/blob/master/art/art_1.png)