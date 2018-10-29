package com.cain.awake;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.cain.awake.util.AppManager;

/**
 * h5唤醒页面
 */
public class AwakenActivity extends Activity {

    /**
     * activity 生命周期的onCreate方法
     * @param savedInstanceState   savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            launchApp(getIntent().getData());
            finish();
        }catch (Exception e){
            e.printStackTrace();
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null){
            launchApp(intent.getData());
        }
    }

    private void launchApp(Uri uridata) {
        //Uri uridata = this.getIntent().getData();
        //monday://cain/october?userId=11

        if (uridata != null && uridata.toString().contains("monday://cain/october")){
            String userId = uridata.getQueryParameter("userId");

            Log.e("s",uridata.toString());

            Intent intent = new Intent(this, WebActivity.class);
            if (AppManager.getAppManager().isOpenActivity(MainActivity.class)){
                startActivity(intent);
            } else {
                TaskStackBuilder.create(this)
                        .addParentStack(intent.getComponent())
                        .addNextIntent(intent)
                        .startActivities();
            }

        }
    }



}
