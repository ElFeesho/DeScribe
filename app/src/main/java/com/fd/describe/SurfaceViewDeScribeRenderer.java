package com.fd.describe;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceView;

public class SurfaceViewDeScribeRenderer implements DeScribePresenter.DeScribeRenderer {
    private SurfaceView surfaceView;
    private Bitmap canvasBuffer;
    private Canvas canvas;
    private Paint paint;
    private Paint erasePaint;
    private Path currentPathBuffer;

    private DeScribePresenter.DeScribeView.DeScribePoint lastPoint;

    public SurfaceViewDeScribeRenderer(SurfaceView view) {
        surfaceView = view;
        paint = new Paint();
        paint.setColor(0xff000000);
        paint.setStrokeWidth(4.0f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        erasePaint = new Paint();
        erasePaint.setColor(0xffffffff);
        currentPathBuffer = new Path();
    }

    @Override
    public void beginPath(DeScribePresenter.DeScribeView.DeScribePoint point) {
        currentPathBuffer.reset();
        currentPathBuffer.moveTo(point.x, point.y);
        lastPoint = point;
        updateStrokeWidth(point);
    }

    @Override
    public void endPath(DeScribePresenter.DeScribeView.DeScribePoint point) {

    }

    @Override
    public void quadTo(DeScribePresenter.DeScribeView.DeScribePoint point) {

        updateStrokeWidth(point);
        currentPathBuffer.quadTo(lastPoint.x, lastPoint.y, (point.x + lastPoint.x) / 2, (point.y + lastPoint.y) / 2);
        canvas.drawPath(currentPathBuffer, paint);

        currentPathBuffer.reset();
        currentPathBuffer.setLastPoint((point.x + lastPoint.x) / 2, (point.y + lastPoint.y) / 2);

        lastPoint = point;

        postUpdate();
    }

    private void updateStrokeWidth(DeScribePresenter.DeScribeView.DeScribePoint point) {
        float targetWidth = 10.0f * (point.pressure * 1.5f);
        float currentWidth = paint.getStrokeWidth();
        if (Math.abs(targetWidth - currentWidth) < 1.0f) {
            paint.setStrokeWidth(targetWidth);
        } else {
            paint.setStrokeWidth((targetWidth < currentWidth) ? (currentWidth - 1.0f) : (currentWidth + 1.0f));
        }
    }

    @Override
    public void setCanvasSize(int width, int height) {
        canvasBuffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(canvasBuffer);
        canvas.drawColor(0xffffffff);
        postUpdate();
    }

    private void postUpdate() {
        Canvas surfaceCanvas = surfaceView.getHolder().lockCanvas();
        surfaceCanvas.drawBitmap(canvasBuffer, 0, 0, paint);
        surfaceView.getHolder().unlockCanvasAndPost(surfaceCanvas);
    }

    @Override
    public void setLineThickness(float thickness) {
        paint.setStrokeWidth(thickness);
    }

    @Override
    public void setColour(int colour) {
        paint.setColor(colour);
    }

    @Override
    public void erasePathAt(DeScribePresenter.DeScribeView.DeScribePoint point) {
        canvas.drawCircle(point.x, point.y, 30.f, erasePaint);
        postUpdate();
    }
}