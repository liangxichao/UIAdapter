用法：
在工程的的基类 Activity 的 onCreate 生命周期方法里添加：
    TDLayoutMgr.init(this);
    如：
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // 该句代码会多次调用，但只会执行一次
            TDLayoutMgr.init(this);
        }
即可
        