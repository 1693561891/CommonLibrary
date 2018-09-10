package com.hubertyoung.weiboplatforms.platforms.weibo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hubertyoung.baseplatform.sdk.IResult;
import com.hubertyoung.baseplatform.tools.PayLogUtil;


public class WBShareActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PayLogUtil.loge(WBShare.TAG, "==> onCreate");
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        PayLogUtil.loge(WBShare.TAG, "==> " + getIntent());

        for (IResult service: WBShare.services.keySet()) {
            if (service != null) {
                service.onResult(WBShare.REQUEST_CODE, Activity.RESULT_OK, intent);
            }
        }
        finish();
    }
}
