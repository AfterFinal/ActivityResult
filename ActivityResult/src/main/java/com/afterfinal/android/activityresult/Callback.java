package com.afterfinal.android.activityresult;

import android.content.Intent;

public interface Callback {
    void onActivityResult(int resultCode, Intent data);
}
