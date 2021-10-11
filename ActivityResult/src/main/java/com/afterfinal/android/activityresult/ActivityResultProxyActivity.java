package com.afterfinal.android.activityresult;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityResultProxyActivity extends AppCompatActivity {
    private static final int REQUEST_TARGET = 0x7031;

    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra(ActivityResult.KEY_ID);
        if (null != id) {
            Log.e("ActivityResult", "Activity can only start through ActivityResult.forXXX");
            finish();
        }
        startActivityForResult(getIntent().getParcelableExtra(ActivityResult.KEY_TARGET_INTENT), REQUEST_TARGET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Callback callback = ActivityResult.callbackMap.get(id);
        if (null != callback) {
            callback.onActivityResult(resultCode, data);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        if (null != id) {
            ActivityResult.dataMap.remove(id);
            ActivityResult.callbackMap.remove(id);
        }
        super.onDestroy();
    }
}
