package com.gamerole.common.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.bulong.rudeness.RudenessScreenHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.zxy.recovery.core.Recovery;

import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * Created by lv on 18-5-28.
 */

public class CommonApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        App.INSTANCE.registerActivityLifecycleCallbacks(new BaseActivityLifecycleCallbacks());
        new RudenessScreenHelper(App.INSTANCE, 750).activate();
        initOkgoHttp(this);
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .recoverEnabled(true)
                .callback(null)
                .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                .init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initOkgoHttp(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
//        builder.addNetworkInterceptor(new StethoInterceptor());
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(context)));
//        builder.addInterceptor(new LogInterceptor());
        builder.addInterceptor(loggingInterceptor);
//---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
//        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
//        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey2", "这里支持中文参数");
//-------------------------------------------------------------------------------------//

        OkGo.getInstance().init(App.INSTANCE)                       //必须调用初始化
                .setOkHttpClient(builder.build())                   //必须设置OkHttpClient
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0);
        //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers)                      //全局公共头
//                .addCommonParams(params);
    }

}
