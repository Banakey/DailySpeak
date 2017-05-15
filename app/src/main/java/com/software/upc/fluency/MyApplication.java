package com.software.upc.fluency;

import android.app.Application;

/**
 * Created by jmy on 2017/5/15.
 */

public class MyApplication extends Application {
    private String name;

    @Override
    public void onCreate() {
        super.onCreate();
        setName(NAME); //初始化全局变量
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private static final String NAME = "MyApplication";
}
