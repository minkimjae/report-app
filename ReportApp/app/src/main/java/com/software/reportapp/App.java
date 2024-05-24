package com.software.reportapp;

import android.app.Application;
import android.content.Context;

/**
 *  context를 전역변수로 가져다 쓰기 위한 클래스
 *  AndroidManifest.xml의 application  android:name=".App" 이 설정 되어야 동작함
 */
public class App extends Application {
    private static App instance;

    public App() {
        super();
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }



}
