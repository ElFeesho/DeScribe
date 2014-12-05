package com.fd.describe;

import android.content.Context;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class SurfaceViewDeScribeView extends SurfaceView implements DeScribePresenter.DeScribeView {
    private Listener listener;

    public static class TouchListenerAdapter implements OnTouchListener {
        private final DrawListener listener;

        public TouchListenerAdapter(DrawListener listener) {
            this.listener = listener;
        }

        private boolean containsStylus(int sources) {
            return (sources & InputDevice.SOURCE_STYLUS) == InputDevice.SOURCE_STYLUS;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            DeScribePoint point = new DeScribePoint(event.getX(), event.getY(), event.getPressure());
            if (!containsStylus(event.getDevice().getSources())) {
                listener.eraseAt(point);
            } else {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    listener.penDown(point);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    listener.lineTo(point);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    listener.penUp(point);
                }
            }
            return true;
        }
    }


    public SurfaceViewDeScribeView(Context context) {
        super(context);
        setKeepScreenOn(true);
        getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                listener.canDraw();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                listener.size(width, height);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                listener.cannotDraw();
            }
        });
    }

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void setDrawListener(DrawListener listener) {
        setOnTouchListener(new TouchListenerAdapter(listener));
    }

    @Override
    public void removeDrawListener() {
        this.setOnTouchListener(null);

    }
}
