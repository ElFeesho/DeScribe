package com.fd.describe;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SurfaceViewDeScribeView view = new SurfaceViewDeScribeView(this);
        new DeScribePresenter(view, new SurfaceViewDeScribeRenderer(view), new DeScribePresenter.ColourPaletteListener() {
            @Override
            public void displayColourPalette() {

            }
        });
        setContentView(view);
    }
}
