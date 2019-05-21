# UIAdapter

## 用法：
在工程的的基类 Activity 的 onCreate 生命周期方法里添加：
``````
    LayoutMgr.init(this);
    如：
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // 注意，要在setContentView(xxx);代码之前执行 
            // 该句代码会多次调用，但只会执行一次
            LayoutMgr.init(this);
        }

        导入自定义控件：
        views 目录下的 
``````
   ![](https://github.com/liangxichao/UIAdapter/blob/master/art/views.PNG)
``````
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
红色框是适配设置后的视图
绿色框是通过dp设置后的视图
在同样屏幕大小下，不同屏幕密度下的视图效果如下：
![](https://github.com/liangxichao/UIAdapter/blob/master/art/example.PNG)
可以看出，绿框的视图的会随着屏幕密度的变化有不同的显示，而红框内的视图则可以保持一致的显示效果