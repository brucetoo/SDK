package com.example.SDK;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.premnirmal.Magnet.IconCallback;
import com.premnirmal.Magnet.Magnet;

/**
 * Class Function:
 * Created By Bruce Too
 * On 2014-11-24 下午 5:38
 */
public class WindowManagerUtil {

    private static DetailView mDetailView;
    private static Magnet magnetView;
    public static ImageView icon_view;
    private static WindowManager.LayoutParams detailViewWindowParams;

//    private static WindowManager mWindowManager;


    public static void createDetailView(Context context, float iconXPose, float iconYPose) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
/*        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();*/

        if (mDetailView == null) {
            mDetailView = new DetailView(context);
            if (detailViewWindowParams == null) {
                detailViewWindowParams = new WindowManager.LayoutParams();
                detailViewWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                detailViewWindowParams.format = PixelFormat.RGBA_8888;
                detailViewWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
                detailViewWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                detailViewWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                detailViewWindowParams.x = (int) iconXPose - DetailView.viewWidth/2;
                detailViewWindowParams.y = (int) iconYPose - 100;
            }
            mDetailView.setLayoutParams(detailViewWindowParams);
            windowManager.addView(mDetailView, detailViewWindowParams);
        }
    }


    public static void removeDetailWindow(Context context) {
        if (mDetailView != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.removeView(mDetailView);
            mDetailView = null;
            detailViewWindowParams = null;
        }
    }

    public static void createMagnetView(final Context context,float progress) {
        ImageView iconView = new ImageView(context);
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
        Matrix matrix = new Matrix();
        matrix.postScale(0.4f, 0.4f);
        iconView.setImageBitmap(bm.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true));

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.icon_view,null);
        icon_view = (ImageView) view.findViewById(R.id.flow_image);
        if(progress != 0) {
            icon_view.setAlpha(progress);
        }
        //iconView.setImageResource(R.drawable.flow);
        // if (magnetView == null) {
        magnetView = new Magnet.Builder(context)
                .setIconView(view)
                .setIconCallback(new IconCallback() {
                    @Override
                    public void onFlingAway() {

                    }

                    @Override
                    public void onMove(float x, float y) {

                    }

                    @Override
                    public void onIconClick(View icon, float iconXPose, float iconYPose) {
                        createDetailView(context, iconXPose
                                , iconYPose);
                        magnetView.destroy();
                    }

                    @Override
                    public void onIconDestroyed() {

                    }
                })
                .setRemoveIconResId(R.drawable.trash)
                .setRemoveIconShadow(R.drawable.bottom_shadow)
                .setShouldFlingAway(true)
                .setShouldStickToWall(true)
                .setRemoveIconShouldBeResponsive(true)
                .build();
        // }
        magnetView.show();
    }

    public static void removeAllView() {
        if (magnetView != null) {
            magnetView.destroy();
            magnetView = null;
        }
        if (mDetailView != null) {
            mDetailView = null;
        }
    }
}
