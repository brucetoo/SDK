package com.example.SDK;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Class Function:
 * Created By Bruce Too
 * On 2014-11-24 下午 2:18
 */
public class SDKService extends Service {
    private static final String TAG = "Magnet";
/*    private Magnet mMagnet;
    private WindowManager mWindowManager;*/

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onStart(Intent intent, int startId) {
       // super.onStart(intent, startId);
       float progress =  0;
       // if(progress == 0){
            progress = SharePreUtils.getFloat(getApplicationContext(),"prog");
      // }
       WindowManagerUtil.removeAllView();
       WindowManagerUtil.createMagnetView(this,progress);
      //  WindowManagerUtil.createDetailView(this,0,0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
