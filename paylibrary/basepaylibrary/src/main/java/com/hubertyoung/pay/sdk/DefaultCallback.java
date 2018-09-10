package com.hubertyoung.pay.sdk;

import android.app.Activity;

/**
 * Created by ezy on 17/3/18.
 */

public class DefaultCallback<R> implements OnCallback<R> {
    OnCallback<R> onCallback;
    OnSuccess<R> onSuccess;

    public DefaultCallback(OnCallback<R> callback, OnSuccess<R> success) {
        onCallback = callback;
        onSuccess = success;
    }

    @Override
    public void onStart( Activity activity) {
        if (onCallback != null) {
            onCallback.onStart(activity);
        }
    }

    @Override
    public void onCompleted(Activity activity) {
        if (onCallback != null) {
            onCallback.onCompleted(activity);
        }
    }

    @Override
    public void onError( Activity activity, int code, String message) {
        if (onCallback != null) {
            onCallback.onError(activity, code, message);
        }
    }

    @Override
    public void onSuccess( Activity activity, R result) {
        if (onCallback != null) {
            onCallback.onSuccess(activity, result);
        }
        if (onSuccess != null) {
            onSuccess.onSuccess(result);
        }
    }
}
