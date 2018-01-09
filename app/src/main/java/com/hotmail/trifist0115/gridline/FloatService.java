package com.hotmail.trifist0115.gridline;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Tristan (wpcheng@iflytek.com) on 2017/12/30.
 */

public class FloatService extends Service {
    private WindowManager windowManager;
    private LinearLayout smallLayout;
    private FrameLayout gradlineLayout;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        createSmallView();
        createGridlineView();
    }

    public void createSmallView() {
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        wmParams.gravity = Gravity.END | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = 100;
        wmParams.height = 100;
        LayoutInflater inflater = LayoutInflater.from(this);
        smallLayout = (LinearLayout) inflater.inflate(R.layout.small_float, null);
        ImageView smallFloatContent = (ImageView) smallLayout.findViewById(R.id.small_float_content);
        smallFloatContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smallLayout.setVisibility(View.GONE);
                gradlineLayout.setVisibility(View.VISIBLE);
            }
        });
        windowManager.addView(smallLayout, wmParams);
    }

    public void createGridlineView() {
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 200;
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = getDisplaysMetrics()[0] - 500;
        LayoutInflater inflater = LayoutInflater.from(this);
        gradlineLayout = (FrameLayout) inflater.inflate(R.layout.gridline_float, null);
        final GridlineView gridlineView = (GridlineView) gradlineLayout.findViewById(R.id.my_gridline_view);
        Button close = (Button) gradlineLayout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smallLayout.setVisibility(View.VISIBLE);
                gradlineLayout.setVisibility(View.GONE);
            }
        });
        Button jump = (Button) gradlineLayout.findViewById(R.id.jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridlineView.jump();
            }
        });
        windowManager.addView(gradlineLayout, wmParams);
        gradlineLayout.setVisibility(View.GONE);
    }

    private int[] getDisplaysMetrics() {
        DisplayMetrics displaysMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displaysMetrics);
        int metrics[] = new int[]{displaysMetrics.heightPixels,
                displaysMetrics.widthPixels};
        return metrics;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
