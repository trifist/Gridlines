package com.hotmail.trifist0115.gridline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.input.InputManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Tristan (wpcheng@iflytek.com) on 2017/12/30.
 */

public class GridlineView extends View {
    private Paint gridlinePaint;
    private Paint distancePaint;
    private Paint textPaint;
    private TouchState currentState = TouchState.state_clear;
    private Point p1 = new Point();
    private Point p2 = new Point();
    private double distance;
    private InputManager im;

    public GridlineView(Context context) {
        super(context);
        im = (InputManager) context.getSystemService(Context.INPUT_SERVICE);
        initPaint();
    }

    public GridlineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        im = (InputManager) context.getSystemService(Context.INPUT_SERVICE);
        initPaint();
    }

    public GridlineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        im = (InputManager) context.getSystemService(Context.INPUT_SERVICE);
        initPaint();
    }

    private void initPaint() {
        gridlinePaint = new Paint();
        gridlinePaint.setColor(Color.RED);
        gridlinePaint.setStrokeWidth(1.0f);
        distancePaint = new Paint();
        distancePaint.setColor(Color.BLACK);
        distancePaint.setStrokeWidth(3.0f);
        textPaint = new Paint();
        textPaint.setStrokeWidth(3);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sideLength = Math.min(getMeasuredWidth() / 10, getMeasuredHeight() / 10);
        for (int i = sideLength; i < getMeasuredHeight(); i += sideLength) {
            canvas.drawLine(0, i, getMeasuredWidth(), i, gridlinePaint);
        }

        for (int i = sideLength; i < getMeasuredWidth(); i += sideLength) {
            canvas.drawLine(i, 0, i, getMeasuredHeight(), gridlinePaint);
        }

        if (currentState == TouchState.state_p1) {
            canvas.drawCircle(p1.x, p1.y, 10.0f, distancePaint);
        } else if (currentState == TouchState.state_p2) {
            canvas.drawCircle(p1.x, p1.y, 10.0f, distancePaint);
            canvas.drawCircle(p2.x, p2.y, 10.0f, distancePaint);
            canvas.drawLine(p1.x, p1.y, p2.x, p2.y, distancePaint);
            distance = Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));
            canvas.drawText(distance * 1.35 + "", 5, 150, textPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void jump() {
        final String time = (distance * 1.35) + "";
        if (distance > 0) {
//            new Thread() {
//                @Override
//                public void run() {
//                    execShellCmd("input swipe 100 100 100 100 " + time);
//                }
//            }.start();
            try {
                new ProcessBuilder(new String[]{"input", "swipe", "100", "800", "100", "800", "1000"}).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void execShellCmd(String cmd) {

        try {
            Process process = Runtime.getRuntime().exec("su");
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
            process.waitFor();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    private void touchDown(MotionEvent event) {
        switch (currentState) {
            case state_clear:
                p1.set((int) event.getX(), (int) event.getY());
                currentState = TouchState.state_p1;
                break;
            case state_p1:
                p2.set((int) event.getX(), (int) event.getY());
                currentState = TouchState.state_p2;
                break;
            case state_p2:
                p1.set(0, 0);
                p2.set(0, 0);
                currentState = TouchState.state_clear;
                break;
            default:
                break;
        }
        invalidate();
    }

    private enum TouchState {
        state_p1, state_p2, state_clear
    }
}
