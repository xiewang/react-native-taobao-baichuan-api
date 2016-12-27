package com.react.taobaobaichuanapi;

import android.widget.Toast;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Map;

public class BaiChuanModule extends ReactContextBaseJavaModule {

  public BaiChuanModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "React_Native_Taobao_Baichuan_Api";
  }

  @ReactMethod
  public void jump(String itemId) {
    
  }
}