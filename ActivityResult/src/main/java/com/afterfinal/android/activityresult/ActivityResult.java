package com.afterfinal.android.activityresult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
public class ActivityResult {
    static final String KEY_ID = "ACTIVITY_RESULT_KEY_ID_svcxfsdf@@#$%^&sdv%4$32";
    static final String KEY_TARGET_INTENT = "ACTIVITY_RESULT_KEY_TARGET_INTENT_svcxfsdf@@#$%^&sdv%4$32";

    static final Map<String, Object> dataMap = new HashMap<>();
    static final Map<String, Callback> callbackMap = new HashMap<>();

    public static void forResult(Context context, Class targetActivityClass, @NonNull Callback callback) {
        forResult(context, new Intent(context, targetActivityClass), null, callback);
    }

    public static void forResult(Context context, Class targetActivityClass, Object data, @NonNull Callback callback) {
        forResult(context, new Intent(context, targetActivityClass), data, callback);
    }

    public static void forResult(Context context, Intent intent, @NonNull Callback callback) {
        forResult(context, intent, null, callback);
    }

    public static void forResult(Context context, Intent target, Object data, @NonNull Callback callback) {
        String id = genId(callback);
        dataMap.put(id, data);
        callbackMap.put(id, callback);
        target.putExtra(KEY_ID, id);
        Intent intent = new Intent(context, ActivityResultProxyActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_TARGET_INTENT, target);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    private static String genId(Callback callback) {
        return UUID.randomUUID().toString() + "-" + System.currentTimeMillis() + "-" + callback.hashCode();
    }

    public static <T> T getData(Activity activity) {
        String id = activity.getIntent().getStringExtra(KEY_ID);
        return (T) dataMap.get(id);
    }
}
