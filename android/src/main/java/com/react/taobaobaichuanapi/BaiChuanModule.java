package com.react.taobaobaichuanapi;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;

import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import android.app.Activity;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import android.content.Intent;
import com.facebook.react.bridge.Promise;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.ali.auth.third.core.model.Session;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;

import java.util.Map;
import java.util.HashMap;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class BaiChuanModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private Promise promise;
    private final static String NOT_LOGIN = "not login";
    private EventManager eventManager;

    public BaiChuanModule(ReactApplicationContext reactContext) {

        super(reactContext);
        reactContext.addActivityEventListener(this);
        this.eventManager = new EventManager(reactContext);
    }

    @Override
    public String getName() {
        return "React_Native_Taobao_Baichuan_Api";
    }

    @ReactMethod
    public void jump(String itemId, String orderId,String type,Callback successCallback ) {
//        if (this.getUserInfo().hasKey("userNick")){
            /**
             * 打开电商组件, 使用默认的webview打开
             *
             * @param activity             必填
             * @param tradePage            页面类型,必填，不可为null，详情见下面tradePage类型介绍
             * @param showParams           show参数
             * @param taokeParams          淘客参数
             * @param trackParam           yhhpass参数
             * @param tradeProcessCallback 交易流程的回调，必填，不允许为null；
             * @return 0标识跳转到手淘打开了, 1标识用h5打开,-1标识出错
             */
            //商品详情page
            AlibcBasePage detailPage = new AlibcDetailPage(itemId);
            //实例化我的订单打开page
            AlibcBasePage ordersPage = new AlibcMyOrdersPage(0, true);
            //设置页面打开方式
            AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, true);
            Map<String, String> exParams = new HashMap<>();
            Activity currentActivity = getCurrentActivity();
            if (currentActivity == null) {
                return;
            }

            final  EventManager eventManager = this.eventManager;
            AlibcTrade.show(currentActivity, (itemId == null ||"".equals(itemId))?ordersPage:detailPage, showParams, null, exParams, new AlibcTradeCallback() {

                @Override
                public void onTradeSuccess(TradeResult tradeResult) {
                    //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                    eventManager.send("backFromTB", null);
                }

                @Override
                public void onFailure(int code, String msg) {
                    //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                    eventManager.send("backFromTB", null);

                }

            });
//        }
    }

    /**
     * 是否登录
     */
    @ReactMethod
    public void isLogin(final Callback callback) {
        callback.invoke(null, AlibcLogin.getInstance().isLogin());
    }

    /**
     * 获取已登录的用户信息---无参数传入
     */
    public  WritableMap getUserInfo() {
        final AlibcLogin alibcLogin = AlibcLogin.getInstance();
        WritableMap map = Arguments.createMap();
        if (alibcLogin.isLogin()) {
            Session session = AlibcLogin.getInstance().getSession();
            map.putString("userNick", session.nick);
            map.putString("avatarUrl", session.avatarUrl);
            map.putString("openId", session.openId);
            map.putString("isLogin", "true");
            return map;
        }  else {
            this.showLogin();
        }
        return map;
    }

    public void showLogin() {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        Activity currentActivity = getCurrentActivity();
        alibcLogin.showLogin(currentActivity,new AlibcLoginCallback() {
            @Override
            public void onSuccess() {
                Session session = AlibcLogin.getInstance().getSession();
                WritableMap map = Arguments.createMap();
                map.putString("userNick", session.nick);
                map.putString("avatarUrl", session.avatarUrl);
                map.putString("openId", session.openId);
                map.putString("isLogin", "true");
            }

            @Override
            public void onFailure(int code, String msg) {
                WritableMap map = Arguments.createMap();
                map.putInt("code", code);
                map.putString("msg", msg);
            }
        });
    }

    @Override
    public void onActivityResult(Activity activity,final int requestCode, final int resultCode, final Intent intent) {
        this.eventManager.send("backFromTB", null);
    }
    @Override
    public void onNewIntent(final Intent intent) {
        this.eventManager.send("backFromTB", null);
    }
}