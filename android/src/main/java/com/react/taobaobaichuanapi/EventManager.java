package com.react.taobaobaichuanapi;

/**
 * Created by wanxie on 5/5/18.
 */

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import android.support.annotation.Nullable;

public class EventManager {
    private ReactContext reactContext;

    public EventManager(ReactContext reactContext) {
        this.reactContext = reactContext;
    }

    public final void send(String event, @Nullable WritableMap params) {
        this.reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(event, params);
    }
}
