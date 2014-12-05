package com.fd.describe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class LinearLayoutColourPalette extends LinearLayout implements DeScribeColourPalettePresenter.DeScribeColourPaletteView {

    private static int[] COLOURS = { 0xff000000, 0xffff00ff, 0xffff0000, 0xff00ff00, 0xff0000ff };

    private Listener listener;

    public LinearLayoutColourPalette(Context context) {
        this(context, null);
    }

    public LinearLayoutColourPalette(Context context, AttributeSet attrs) {
        super(context, attrs);

        for(int i = 0; i < COLOURS.length; i++) {
            View view = new View(context);
            LayoutParams lp = new LayoutParams((int)(getResources().getDisplayMetrics().density*64.0f), (int)(getResources().getDisplayMetrics().density*64.0f));
            view.setLayoutParams(lp);
            view.setBackgroundColor(COLOURS[i]);
            addView(view);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.colourSelected(COLOURS[finalI]);
                }
            });
        }
    }

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void displayColour(int colour) {
        listener.colourSelected(colour);
    }

    @Override
    public void show() {
        animate().translationY(-getMeasuredHeight());
    }

    @Override
    public void hide() {
        animate().translationY(getMeasuredHeight());
    }
}
